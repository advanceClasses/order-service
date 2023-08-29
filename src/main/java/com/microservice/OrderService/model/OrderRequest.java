package com.microservice.OrderService.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Integer quantity;
    private BigDecimal amount;
    private String status;
    private LocalDate date;

}
