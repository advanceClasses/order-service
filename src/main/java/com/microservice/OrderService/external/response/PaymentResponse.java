package com.microservice.OrderService.external.response;

import com.microservice.OrderService.external.request.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long id;
    private PaymentMode mode;
    private String status;
    private long amount;
    private Instant date;
    private long orderId;
}