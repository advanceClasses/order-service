package com.microservice.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.microservice.OrderService.external.request.Mode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    
    private int quantity;
    private long amount;
    private long productId;
    private Mode mode;

}
