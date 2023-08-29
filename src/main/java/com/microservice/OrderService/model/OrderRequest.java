package com.microservice.OrderService.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private long ammount;
    private int quantity;
    private String status;
    private LocalDate date;
    
}
