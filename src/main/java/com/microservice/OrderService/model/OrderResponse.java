package com.microservice.OrderService.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private long quantity, amount;
    private String status;
    private LocalDate date;
}
