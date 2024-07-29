package com.example.shop.controller.admin;

import com.example.shop.model.Delivery;
import com.example.shop.model.Order;
import com.example.shop.model.OrderDetails;
import com.example.shop.model.Payment;
import com.example.shop.respository.OrderDetailRepository;
import com.example.shop.respository.OrderRepository;
import com.example.shop.service.DeliveryService;
import com.example.shop.service.OrderDetailService;
import com.example.shop.service.OrderService;
import com.example.shop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private PaymentService paymentService;

    public OrderController(OrderService orderService, OrderRepository orderRepository, OrderDetailService orderDetailService, OrderDetailRepository orderDetailRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.orderDetailRepository = orderDetailRepository;
    }
    @GetMapping("")
    public String getAllOrder(Model model){
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orders", orderList);
        List<Delivery> deliveryList = deliveryService.getAllDelivery();
        model.addAttribute("deliveryList",deliveryList);
        return "admin/order/index";
    }
   @GetMapping("/{id}")
   public String  getOrderById(@PathVariable(value = "id") long id,Model model){
       List<OrderDetails> orderDetails = orderDetailRepository.findOrdersByOrderId(id);
       model.addAttribute("orderDetails",orderDetails);
       Order order = orderRepository.findOrderById(id);
       List<Delivery> deliveryList = deliveryService.getAllDelivery();
       model.addAttribute("deliveryList",deliveryList);
       List<Payment> paymentList=  paymentService.getAllPayment();
       model.addAttribute("paymentList",paymentList);
       long total = order.getTotalPrice();
       model.addAttribute("order",order);
       model.addAttribute("total",total);
       return "admin/order/detail";
   }
   @PostMapping("/saveOrder/{id}")
    public String saveOrder(@ModelAttribute("Order") Order order, @PathVariable(value = "id") long id){
       orderService.updateOrder(order,id);
        return "redirect:/admin/orders";
   }
}
