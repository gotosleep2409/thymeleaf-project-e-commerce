package com.example.shop.service;

import com.example.shop.model.Order;
import com.example.shop.model.OrderDetails;

import java.util.List;

public interface OrderService {
    /*Order saveOrder(Order order, OrderDetails orderDetails);*/
    Order saveOrder(Order order);

    Order updateOrder(Order order, long id);

}
