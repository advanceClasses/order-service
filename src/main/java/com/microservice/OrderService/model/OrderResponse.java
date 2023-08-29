package com.microservice.OrderService.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long id;
    private long productId;
    private long quantity;
    private long amount;
    private String status;
    private LocalDate date;
}
