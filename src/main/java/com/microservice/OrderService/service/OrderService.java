package com.microservice.OrderService.service;

import java.time.Instant;
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
    return orderRepository.findAll()
        .stream()
        .map(order -> {
          return orderResponseMap(order);
        }).collect(Collectors.toList());
  }

  public OrderResponse getById(long id) {
    log.info("Get order by id {}", id);
    Order order = orderRepository
        .findById(id)
        .orElseThrow(() -> new CustomException("Order with id " + id + " not found", "ORDER_NOT_FOUND", 404));
    return orderResponseMap(order);
  }

  public OrderResponse create(OrderRequest orderRequest) {
    log.info("Order data form user : {}", orderRequest);

    // check product availability
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
        .date(Instant.now())
        .build();

    // create an order with `CREATED` status
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
      // create payment data
      PaymentResponse payment = restTemplate.exchange(
          "http://PAYMENT-SERVICE/payment",
          HttpMethod.POST,
          new HttpEntity(paymentRequest),
          new ParameterizedTypeReference<PaymentResponse>() {
          }).getBody();
      log.info("Payment done successfully with id {}", payment.getId());
      orderStatus = "PLACED";

      // reduce product quantity
      restTemplate.exchange(
          "http://PRODUCT-SERVICE/product/reduceQuantity/" + order.getProductId() + "?quantity=" + order.getQuantity(),
          HttpMethod.PUT,
          null,
          new ParameterizedTypeReference<Void>() {
          });
      log.info("Successfully to Reduce Quantity of product with id {}", order.getProductId());

    } catch (Exception e) {
      log.error("Error occurred in payment");
      orderStatus = "PAYNENT_FAILD";
    }

    // update order status
    order.setStatus(orderStatus);
    orderRepository.save(order);
    log.info("Update Status Order with Id {}", order.getId());

    return orderResponseMap(order);
  }

  public OrderResponse orderResponseMap(Order order) {
    OrderResponse orderResponse = new OrderResponse();
    BeanUtils.copyProperties(order, orderResponse);
    ProductResponse product = getProductById(order.getProductId());
    orderResponse.setProduct(product);
    return orderResponse;
  }

  public ProductResponse getProductById(long id) {
    ProductResponse productResponse = restTemplate.exchange(
        "http://PRODUCT-SERVICE/product/" + id,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<ProductResponse>() {
        }).getBody();
    return productResponse;
  }
}