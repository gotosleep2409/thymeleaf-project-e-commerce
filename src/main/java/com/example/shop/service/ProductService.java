package com.example.shop.service;

import com.example.shop.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProduct();
    Product getProductById(long id);
    Product updateProduct(Product product, long id);
    void deleteProduct(long id);

}
