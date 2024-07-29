package com.example.shop.controller.admin;

import com.example.shop.model.Product;
import com.example.shop.service.ProductService;
import com.example.shop.serviceImpl.OrderDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GraphController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @GetMapping("/chart")
    public String chart(Model model){
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        System.out.println(orderDetailService.countOrdersByProduct());
        Map<String, Long> dataa = orderDetailService.countOrdersByProduct();
        List<Product> productList = productService.getAllProduct();
        for(Product product : productList){
            data.put(product.getName(), (int) product.getPrice());
        }
        model.addAttribute("data3",dataa);
        model.addAttribute("data",data.keySet());
        model.addAttribute("values",data.values());
        return "admin/chart";
    }
}
