package com.example.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    private String description;
    private String detail;
    private String image;
    @Column(name = "price", nullable = false)
    private long price;
    private String Quantity;
    private long priceSale;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
