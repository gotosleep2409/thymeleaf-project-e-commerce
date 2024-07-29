package com.example.shop.controller.admin;

import com.example.shop.model.Product;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import com.example.shop.serviceImpl.OrderDetailServiceImpl;
import com.example.shop.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    private OrderServiceImpl orderService;
    /*@GetMapping("/")
    public String index() {
        return "redirect:/admin/index";
    }

    @RequestMapping ("/")
    public String admin() {
        return "admin/index";
    }*/
    @GetMapping("/")
    public String index(Model model) {
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        /*System.out.println(orderDetailService.countOrdersByProduct());*/
        Map<String, Long> dataa = orderDetailService.countOrdersByProduct();
        List<Product> productList = productService.getAllProduct();
        for(Product product : productList){
            data.put(product.getName(), (int) product.getPrice());
        }
        model.addAttribute("data3",dataa);
        model.addAttribute("data1",data);
        model.addAttribute("productList",productList);
        Map<YearMonth, Double> totalAmountByMonth = orderService.getTotalAmountByMonth();
        model.addAttribute("totalAmountByMonth",totalAmountByMonth);
        double totalIncome = totalAmountByMonth.values().stream().mapToDouble(Double::doubleValue).sum();
        model.addAttribute("total",totalIncome);
        return "admin/index";
    }
}

