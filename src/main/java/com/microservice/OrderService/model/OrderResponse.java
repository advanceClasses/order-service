package com.microservice.OrderService.model;

import java.time.LocalDate;

import com.microservice.OrderService.external.response.ProductResponse;
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
    private long amount;
    private int quantity;
    private String status;
    private LocalDate date;
    private ProductResponse product;

    
}
