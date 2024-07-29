package com.example.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private long productId;
    private String name;
    private long price;
    private int quantity;
}
