package com.microservice.OrderService.external.response;

import java.time.LocalDate;
import com.microservice.OrderService.external.request.paymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private long id;
    private paymentMode mode;
    private String status;
    private long amount;
    private LocalDate date;
    private long orderId;
    
}
