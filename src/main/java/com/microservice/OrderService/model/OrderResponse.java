package com.microservice.OrderService.model;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.microservice.OrderService.config.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    
    private long id, quantity, amount, productId;
    private String status;

    // @DateTimeFormat(pattern = "dd MMMM yyyy")
    // @JsonFormat(pattern = "dd MMMM yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
    
}