package com.microservice.OrderService.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long amount;
    private String status;
    private Date date;
    
}


/* 
 * Tugas 1 Microservices
 * Silakan membuat branch dengan nama kalian masing-masing.
 * Silakan membuat projet baru dengan nama order-service application dengan dependency (Spring Web, MySQL Driver, Spring Data JPA, Spring Boot DevTools, Cloud Bootstrap dan Lombok)
 * Entity Order attribut (id, quantity, amount, status, date)
 * Silakan membuat CRUD sesuai dengan apa yang sudah saya jelaskan pada Product-Service.
 * Perhatikan logic pada saat Place Order/Create Order.
 * Silakan membaca materi terkait dengan Service Registery.
*/