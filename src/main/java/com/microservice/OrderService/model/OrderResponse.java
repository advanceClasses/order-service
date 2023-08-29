package com.microservice.OrderService.model;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private long quantity;
    private String amount;
    private String status;
    private LocalDateTime date;
}