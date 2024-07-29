package com.example.shop.serviceImpl;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Payment;
import com.example.shop.respository.PaymentRepository;
import com.example.shop.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentServiceImpl implements PaymentService {
    public PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    @Override
    public Payment getPaymentById(long id) {
        return paymentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Payment","Id",id));
    }
    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }
}
