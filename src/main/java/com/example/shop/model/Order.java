package com.example.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String customer;
    private String phone;
    private String address;
    private String email;
    private long totalPrice;
    private long typePayment;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Delivery delivery;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private LocalDate orderDate;
}
