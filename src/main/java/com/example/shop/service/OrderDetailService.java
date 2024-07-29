package com.example.shop.service;

import com.example.shop.model.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    OrderDetails saveOrder(OrderDetails orderDetails);

}
