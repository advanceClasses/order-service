package com.microservice.OrderService.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    
    private int quantity;
    private int amount;
    private int status;
    private LocalDate date;

}
