package com.example.shop.service;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ShoppingCartService {
    public void add(CartItem item);
    public void remove(int id);
    double getAmount();
    int getCount();
    Collection<CartItem> getAllItems();
    void clear();
    CartItem update(int proID, int quantity);
    List<Product> getProducts();

}
