package com.microservice.OrderService.controller;

import com.microservice.OrderService.model.OrderRequest;
import com.microservice.OrderService.model.OrderResponse;
import com.microservice.OrderService.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest OrderRequest) {
        return new ResponseEntity<>(orderService.create(OrderRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable long id, @RequestBody OrderRequest OrderRequest) {
        return new ResponseEntity<>(orderService.update(id, OrderRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> delete(@PathVariable long id) {
        return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
    }

}
