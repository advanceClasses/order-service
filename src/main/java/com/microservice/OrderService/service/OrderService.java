package com.microservice.OrderService.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.exception.CustomException;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderResponse orderResponse = new OrderResponse();
                    BeanUtils.copyProperties(order, orderResponse);
                    return orderResponse;
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found","ORDER_NOT_FOUND",404));
        return orderResponseMap(order);
    }

    public OrderResponse create(OrderRequest orderRequest){
        Order order = new Order();
        BeanUtils.copyProperties(orderRequest, order);
        orderRepository.save(order);
        return orderResponseMap(order);
    }

    public OrderResponse update(Long id, OrderRequest orderRequest){
        getById(id);
        Order order = new Order();
        order.setId(id);
        BeanUtils.copyProperties(orderRequest, order);
        orderRepository.save(order);
        return orderResponseMap(order);
    }

    public OrderResponse delete(Long id){
        OrderResponse orderResponse = getById(id);
        orderRepository.deleteById(id);
        return orderResponse;
    }

    public static OrderResponse orderResponseMap(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        return orderResponse;
    }

}
