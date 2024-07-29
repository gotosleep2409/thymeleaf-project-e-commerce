package com.example.shop.serviceImpl;

import com.example.shop.model.Order;
import com.example.shop.model.OrderDetails;
import com.example.shop.respository.OrderDetailRepository;
import com.example.shop.respository.OrderRepository;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order, long id) {
        Order existingOrder = orderRepository.findOrderById(id);
        existingOrder.setDelivery(order.getDelivery());
        existingOrder.setPayment(order.getPayment());
        return orderRepository.save(existingOrder);
    }
    /*@Override
    public Order saveOrder(Order order, OrderDetails orderDetail) {
        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.setOrderId(savedOrder.getId());
        OrderDetails savedOrderDetail = orderDetailRepository.save(orderDetail);
        return savedOrder;
    }*/
    public Map<YearMonth, Double> getTotalAmountByMonth() {
        List<Order> orders = orderRepository.findAll();
        Map<YearMonth, Double> totalAmountByMonth = orders.stream()
                .collect(Collectors.groupingBy(order -> YearMonth.from(order.getOrderDate()),
                        Collectors.summingDouble(Order::getTotalPrice)));

        return totalAmountByMonth;
    }

}
