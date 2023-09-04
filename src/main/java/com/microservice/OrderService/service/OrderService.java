package com.microservice.OrderService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.external.request.PaymentRequest;
import com.microservice.OrderService.external.response.PaymentResponse;
import com.microservice.OrderService.external.response.ProductResponse;
import com.microservice.OrderService.repository.OrderRepository;
import com.microservice.OrderService.exception.CustomException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.log4j.Log4j2;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Log4j2
public class OrderService {

    private OrderRepository orderRepository;
    private RestTemplate restTemplate;

    public List<OrderResponse> getAll() {
        log.info("Get all order data");
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    return orderResponseMap(order, getProductById(order.getProductId()));
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id) {
        log.info("Get order by id {}", id);
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found", "order_NOT_FOUND", 404));
        return orderResponseMap(order, getProductById(order.getProductId()));
    }

    public OrderResponse create(OrderRequest orderRequest) {
        log.info("Order data form user : {}", orderRequest);

        restTemplate.exchange(
                "http://PRODUCT-SERVICE/product/checkAvail/" + orderRequest.getProductId() + "?quantity="
                        + orderRequest.getQuantity(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Void>() {
                });

        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .status("CREATED")
                .date(LocalDate.now())
                .build();

        orderRepository.save(order);
        
        log.info("Place Order with id {}", order.getId());
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderId(order.getId())
                .mode(orderRequest.getMode())
                .amount(orderRequest.getAmount())
                .build();
        
        String orderStatus;
        try {
            PaymentResponse payment = restTemplate.exchange(
                    "http://PAYMENT-SERVICE/payment",
                    HttpMethod.POST,
                    new HttpEntity(paymentRequest),
                    new ParameterizedTypeReference<PaymentResponse>() {
                    }
            ).getBody();
            log.info("Payment done successfully with id {}", payment.getId());
            orderStatus = "PLACED";

            restTemplate.exchange(
                    "http://PRODUCT-SERVICE/product/reduceQuantity/" + order.getProductId() + "?quantity=" + order.getQuantity(),
                    HttpMethod.PUT,
                    null,
                    new ParameterizedTypeReference<Void>() {
                    }
            );
            log.info("Successfully to Reduce Quantity of product with id {}", order.getProductId());

        }catch (Exception e){
            log.error("Error occurred in payment");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setStatus(orderStatus);
        orderRepository.save(order);
        log.info("Update Status Order with Id {}", order.getId());
        return orderResponseMap(order, getProductById(order.getProductId()));
    }

    public ProductResponse getProductById(long productId){
        return restTemplate.exchange(
            "http://PRODUCT-SERVICE/product/" + productId,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ProductResponse>() {
            }).getBody();
    }

    public static OrderResponse orderResponseMap(Order order, ProductResponse productResponse) {
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        orderResponse.setProduct(productResponse);
        return orderResponse;
    }

}
