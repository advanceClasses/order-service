package com.microservice.OrderService.model;

import com.microservice.OrderService.external.request.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private long productId;
    private int quantity;
    private long amount;
    private PaymentMode mode;
}
