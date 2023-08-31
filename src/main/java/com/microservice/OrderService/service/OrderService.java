package com.microservice.OrderService.service;

import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.exception.CustomException;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class OrderService {

    private OrderRepository orderRepository;

    public List<OrderResponse> getAll(){
        log.info("Get all order data");
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    return orderResponseMap(order);
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id){
        log.info("Get order by id {}",id);
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found","ORDER_NOT_FOUND",404));
        return orderResponseMap(order);
    }

    public OrderResponse create (OrderRequest orderRequest){
        // Check Available product
        // Place Order
        // Payment
            // Reduce the product

        log.info("Order data form user : {}", orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .status("CREATED")
                .date(Instant.now())
                .build();

        orderRepository.save(order);
        log.info("Place Order with id {}",order.getId());
        return orderResponseMap(order);
    }

    public OrderResponse orderResponseMap(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order,orderResponse);
        return orderResponse;
    }
}
