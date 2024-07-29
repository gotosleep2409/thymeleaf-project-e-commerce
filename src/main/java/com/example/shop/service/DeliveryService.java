package com.example.shop.service;

import com.example.shop.model.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery getDeliveryById(long id);

    List<Delivery> getAllDelivery();
}
