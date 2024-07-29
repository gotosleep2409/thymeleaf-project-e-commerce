package com.example.shop.service;

import com.example.shop.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment getPaymentById(long id);
    List<Payment> getAllPayment();
}
