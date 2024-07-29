package com.example.shop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="contents")
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "alias")
    private String alias;
}
