package com.microservice.OrderService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

  private OrderService orderService;

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getAll() {
    return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable long id) {
    return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderRequest) {
    return new ResponseEntity<>(orderService.create(orderRequest), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponse> update(@PathVariable long id, @RequestBody OrderRequest orderRequest) {
    return new ResponseEntity<>(orderService.update(id, orderRequest), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderResponse> delete(@PathVariable long id) {
    return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
  }

}
