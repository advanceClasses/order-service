package com.microservice.OrderService.model;

import com.microservice.OrderService.external.request.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private int quantity;
    private long amount;
    private long productId;
    private PaymentMode mode;

}
