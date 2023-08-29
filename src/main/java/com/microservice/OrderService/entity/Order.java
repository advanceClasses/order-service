package com.microservice.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // Import the LocalDateTime class
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_m_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;
    private String amount;
    private String status;

    @Column(name = "order_date")
    private LocalDateTime date; // Change the data type to LocalDateTime

}
