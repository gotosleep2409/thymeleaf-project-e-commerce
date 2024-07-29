package com.example.shop.serviceImpl;

import com.example.shop.model.Order;
import com.example.shop.model.OrderDetails;
import com.example.shop.model.Product;
import com.example.shop.respository.OrderDetailRepository;
import com.example.shop.service.OrderDetailService;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository ;
    @Override
    public OrderDetails saveOrder(OrderDetails orderDetails) {
        return orderDetailRepository.save(orderDetails);
    }
    public Map<String, Long> countOrdersByProduct() {
        List<OrderDetails> orderDetailsList = orderDetailRepository.findAll();
        Map<String, Long> orderCountByProductName = orderDetailsList.stream()
                .collect(Collectors.groupingBy(orderDetail -> orderDetail.getProduct().getName(),
                        Collectors.counting()));
        return orderCountByProductName;
    }
}
