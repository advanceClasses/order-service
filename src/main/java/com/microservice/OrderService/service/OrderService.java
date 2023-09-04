package com.microservice.OrderService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.excpetion.CustomException;
import com.microservice.OrderService.external.request.PaymentRequest;
import com.microservice.OrderService.external.response.PaymentResponse;
import com.microservice.OrderService.external.response.ProductResponse;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class OrderService {
    private OrderRepository orderRepository;
    private RestTemplate restTemplate;

    public List<OrderResponse> getAll() {
        log.info("Get all order data");
        return orderRepository.findAll().stream().map(order -> {
            return orderResponseMap(order, getProductById(order.getProductId()));
        }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id) {
        log.info("Get order by id {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found", "ORDER_NOT_FOUND", 404));
        return orderResponseMap(order, getProductById(order.getProductId()));
    }

    public OrderResponse create(OrderRequest orderRequest) {
        log.info("Order data: {}", orderRequest);

        restTemplate.exchange(
                "http://PRODUCT-SERVICE/product/check-availability/" + orderRequest.getProductId() + "?quantity="
                        + orderRequest.getQuantity(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Void>() {
                });

        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getAmount())
                .status("Order created")
                .date(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        log.info("Place Order with id {}", order.getId());

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .amount(order.getAmount())
                .mode(orderRequest.getMode())
                .build();

        String orderStatus;
        try {
            PaymentResponse paymentResponse = restTemplate.exchange(
                    "http://PAYMENT-SERVICE/payment", HttpMethod.POST,
                    new HttpEntity<PaymentRequest>(paymentRequest),
                    new ParameterizedTypeReference<PaymentResponse>() {
                    })
                    .getBody();

            log.info("Payment done successfully with id {}", paymentResponse.getId());

            restTemplate.exchange(
                    "http://PRODUCT-SERVICE/product/reduce-quantity/" + order.getProductId() + "?quantity="
                            + order.getQuantity(),
                    HttpMethod.PUT,
                    null,
                    new ParameterizedTypeReference<Void>() {
                    });

            log.info("Successfully reduce quantity of product with id {}", order.getProductId());
            orderStatus = "Order placed";
        } catch (Exception e) {
            log.error("Error occured in payment");
            orderStatus = "Order failed";
        }

        order.setStatus(orderStatus);
        orderRepository.save(order);
        log.info("Update order status with id {}", order.getId());

        return orderResponseMap(order, getProductById(order.getProductId()));
    }

    // public OrderResponse update(long id, OrderRequest orderRequest) {
    // OrderResponse data = getById(id);
    // Order order = Order.builder()
    // .productId(orderRequest.getProductId())
    // .quantity(orderRequest.getQuantity())
    // .amount(orderRequest.getAmount())
    // .status(orderRequest.getStatus())
    // .date(data.getDate())
    // .build();
    // BeanUtils.copyProperties(orderRequest, order);
    // orderRepository.save(order);
    // return orderResponseMap(order);
    // }

    // public OrderResponse delete(long id) {
    // OrderResponse orderResponse = getById(id);
    // orderRepository.deleteById(id);
    // return orderResponse;
    // }

    public ProductResponse getProductById(long productId) {
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
