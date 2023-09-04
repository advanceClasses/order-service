package com.microservices.OrderService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.OrderService.external.request.paymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private int quantity;
    private long amount;
    private String status;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private long productId;
    private paymentMode mode;
}
