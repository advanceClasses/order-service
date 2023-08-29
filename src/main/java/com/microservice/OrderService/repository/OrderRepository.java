package com.microservice.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.OrderService.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {    
}
