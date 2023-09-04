package com.microservice.OrderService.model;

import com.microservice.OrderService.external.request.paymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private long amount;
    private int quantity;
    private long productId;
    private paymentMode mode;
    
}
