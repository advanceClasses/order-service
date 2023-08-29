package com.microservice.OrderService.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.repository.OrderRepository;
import com.microservice.OrderService.exception.CustomException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public List<OrderResponse> getAll(){
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderResponse orderResponse = new OrderResponse();
                    BeanUtils.copyProperties(order,orderResponse);
                    return orderResponse;
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found","order_NOT_FOUND",404));
        return orderResponseMap(order);
    }


    public OrderResponse create(OrderRequest orderRequest){
        Order order = new Order();
        BeanUtils.copyProperties(orderRequest,order);
        order.setDate(LocalDate.now());
        orderRepository.save(order);
        return orderResponseMap(order);
    }

    public OrderResponse update(long id, OrderRequest orderRequest){
        getById(id);
        Order order = new Order();
        order.setId(id);
        BeanUtils.copyProperties(orderRequest, order); 

//        order.setName(orderRequest.getName());
//        order.setPrice(orderRequest.getPrice());
//        order.setQuantity(orderRequest.getQuantity());

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
        BeanUtils.copyProperties(order,orderResponse);
        return orderResponse;
    }
    
}
