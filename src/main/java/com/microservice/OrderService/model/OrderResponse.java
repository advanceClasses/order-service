package com.microservice.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long id;
    private int quantity;
    private long amount;
    private String status;
    private Instant date;
    private long productId;

}
