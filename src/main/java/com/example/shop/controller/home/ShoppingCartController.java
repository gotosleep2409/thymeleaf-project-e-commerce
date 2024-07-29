package com.example.shop.controller.home;

import com.example.shop.config.Config;
import com.example.shop.model.*;
import com.example.shop.respository.CategoryRepository;
import com.example.shop.respository.ContentRepository;
import com.example.shop.respository.ProductRepository;
import com.example.shop.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("shopping-cart")
public class ShoppingCartController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private  PaymentService paymentService;
    @GetMapping ("")
    public String viewCart(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        Collection<CartItem> products= shoppingCartService.getAllItems();
        model.addAttribute("cartItems", products);
        Double total = shoppingCartService.getAmount();
        model.addAttribute("total",total);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "home/shopping-cart";
    }
    @GetMapping("add/{id}")
    public String addCart(@PathVariable("id") Integer id){
        Product product = productService.getProductById(id);
        if(product!= null){
            CartItem item= new CartItem();
            item.setProductId((int) product.getId());
            item.setName(product.getName());
            item.setQuantity(1);
            item.setPrice(product.getPrice());
            shoppingCartService.add(item);
        }
        return "redirect:/shopping-cart";
    }
    @GetMapping("clear")
    public String clearCart(){
        shoppingCartService.clear();
        return "redirect:/shopping-cart";
    }
    @GetMapping("delete/{id}")
    public String removeCart(@PathVariable("id") Integer id){
        shoppingCartService.remove(id);
        return "redirect:/shopping-cart";
    }
    @PostMapping("update")
    public String update(@RequestParam("id") Integer id, @RequestParam("quantity")Integer quantity){
        shoppingCartService.update(id,quantity);
        return "redirect:/shopping-cart";
    }
    @GetMapping("checkout")
    public String checkOut(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("order", new Order());
        return "home/checkout";
    }
    /*@PostMapping("checkout")
    public String checkOut(@ModelAttribute("Order") Order order,Model model) {
        Random random = new Random();
        int randomInt = random.nextInt(9999) + 1;
        order.setTotalPrice((long) shoppingCartService.getAmount());
        long typePayment = order.getTypePayment();
        order.setCode("DH"+randomInt);
        List<Product> products = shoppingCartService.getProducts();
        List<OrderDetails> orderDetails = new ArrayList<>();
        orderService.saveOrder(order);
        for (Product product : products) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(product.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);
            orderDetailService.saveOrder(orderDetail);
        }
        shoppingCartService.clear();
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "home/checkOutSuccess";
    }*/

    @PostMapping("checkout")
    public String checkOut(@ModelAttribute("Order") Order order,Model model, HttpSession session) {
        Random random = new Random();
        int randomInt = random.nextInt(9999) + 1;
        order.setTotalPrice((long) shoppingCartService.getAmount());
        session.setAttribute("orderSession", order);
        long typePayment = order.getTypePayment();
        Payment payment = paymentService.getPaymentById(1);
        if(typePayment == 1){
            Delivery deliveryStatus = deliveryService.getDeliveryById(1);
            order.setOrderDate(LocalDate.now());
            order.setPayment(payment);
            order.setDelivery(deliveryStatus);
            order.setCode("DH"+randomInt);
            List<Product> products = shoppingCartService.getProducts();
            List<OrderDetails> orderDetails = new ArrayList<>();
            orderService.saveOrder(order);
            for (Product product : products) {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(product.getQuantity());
                orderDetail.setPrice(product.getPrice());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
                orderDetailService.saveOrder(orderDetail);
            }
            shoppingCartService.clear();
            List<Contents> contents = contentRepository.findAll();
            model.addAttribute("contents", contents);
            int Count = shoppingCartService.getCount();
            model.addAttribute("count",Count);
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "home/checkOutCODSuccess";
        }
        return "redirect:/shopping-cart/pay";
    }
    @GetMapping("/pay")
    public String getPay(HttpSession session) throws UnsupportedEncodingException {
        Order order = (Order) session.getAttribute("orderSession");
        if (order == null) {
            return "redirect:/shopping-cart";
        }
        else {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long total= (long) shoppingCartService.getAmount();
            long amount = total*100;
            String bankCode = "NCB";
            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";
            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

/*
            vnp_Params.put("vnp_BankCode", bankCode);
*/
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
            return "redirect:"+paymentUrl;
        }
    }
    @GetMapping("/paymentSuccess")
    public String paymentSuccess(@RequestParam(name = "vnp_TransactionStatus")String status,Model model,HttpSession session ){
        Order order = (Order) session.getAttribute("orderSession");
        Delivery deliveryStatus = deliveryService.getDeliveryById(1);
        Payment payment = paymentService.getPaymentById(2);
        Random random = new Random();
        int randomInt = random.nextInt(9999) + 1;
        if(status.equals("00")){
            order.setOrderDate(LocalDate.now());
            order.setCode("DH"+ randomInt);
            order.setPayment(payment);
            order.setTypePayment(2);
            order.setDelivery(deliveryStatus);
            List<Product> products = shoppingCartService.getProducts();
            List<OrderDetails> orderDetails = new ArrayList<>();
            orderService.saveOrder(order);
            for (Product product : products) {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(product.getQuantity());
                orderDetail.setPrice(product.getPrice());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
                orderDetailService.saveOrder(orderDetail);
            }
        }
        model.addAttribute("status",status);
        shoppingCartService.clear();
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "home/checkOutSuccess";
    }
}
