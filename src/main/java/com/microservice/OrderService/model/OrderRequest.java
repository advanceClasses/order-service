package com.microservice.OrderService.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private int quantity;
    private int amount;
    private String status;
    private Date date;

}
