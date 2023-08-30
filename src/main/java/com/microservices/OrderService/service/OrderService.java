package com.microservices.OrderService.service;

import com.microservices.OrderService.entity.Order;
import com.microservices.OrderService.excepetion.CustomException;
import com.microservices.OrderService.model.OrderRequest;
import com.microservices.OrderService.model.OrderResponse;
import com.microservices.OrderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public List<OrderResponse> getAll(){
        return orderRepository.findAll()
                .stream()
                .map(order ->{
                    OrderResponse orderResponse = new OrderResponse();
                    BeanUtils.copyProperties(order, orderResponse);
                    return orderResponse;
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found", "PRODUCT_NOT_FOUND", 404));
        return orderResponseMap(order);
    }

    public OrderResponse create(OrderRequest orderRequest){
        Order order = new Order();
        BeanUtils.copyProperties(orderRequest, order);
        orderRepository.save(order);
        return orderResponseMap(order);
    }

    public OrderResponse update(long id, OrderRequest orderRequest){
        getById(id);
        Order order = new Order();
        order.setId(id);
        BeanUtils.copyProperties(orderRequest, order);
        orderRepository.save(order);
        return orderResponseMap(order);
    }

    public OrderResponse delete(long id){
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
