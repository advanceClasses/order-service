package com.microservice.OrderService.service;

import com.microservice.OrderService.entity.Order;
import com.microservice.OrderService.exception.CustomException;
import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository OrderRepository;

    public List<OrderResponse> getAll() {
        return OrderRepository.findAll()
                .stream()
                .map(Order -> {
                    OrderResponse OrderResponse = new OrderResponse();
                    BeanUtils.copyProperties(Order, OrderResponse);
                    return OrderResponse;
                }).collect(Collectors.toList());
    }

    public OrderResponse getById(long id) {
        Order Order = OrderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order with id " + id + " not found", "Order_NOT_FOUND", 404));
        return OrderResponseMap(Order);
    }

    public OrderResponse create(OrderRequest OrderRequest) {
        Order Order = new Order();
        BeanUtils.copyProperties(OrderRequest, Order);
        OrderRepository.save(Order);
        return OrderResponseMap(Order);
    }

    public OrderResponse update(long id, OrderRequest OrderRequest) {
        getById(id);
        Order Order = new Order();
        Order.setId(id);
        BeanUtils.copyProperties(OrderRequest, Order); // ModelMapper

        // Order.setName(OrderRequest.getName());
        // Order.setPrice(OrderRequest.getPrice());
        // Order.setQuantity(OrderRequest.getQuantity());

        OrderRepository.save(Order);
        return OrderResponseMap(Order);
    }

    public OrderResponse delete(long id) {
        OrderResponse OrderResponse = getById(id);
        OrderRepository.deleteById(id);
        return OrderResponse;
    }

    public static OrderResponse OrderResponseMap(Order Order) {
        OrderResponse OrderResponse = new OrderResponse();
        BeanUtils.copyProperties(Order, OrderResponse);
        return OrderResponse;
    }
}
