package com.example.shop.controller.home;

import com.example.shop.model.Category;
import com.example.shop.model.Contents;
import com.example.shop.model.Product;
import com.example.shop.respository.CategoryRepository;
import com.example.shop.respository.ContentRepository;
import com.example.shop.respository.ProductRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {
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

    public HomeController(CategoryRepository categoryRepository,ShoppingCartService shoppingCartService, ContentRepository contentRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.contentRepository = contentRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
    }
    @GetMapping("")
    public String getAllContent(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Product> products = productRepository.findProductSale();
        model.addAttribute("productSale", products);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        return "home/home"  ;
    }
    @GetMapping("/categories")
    public String getAllProduct(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Product> products = productRepository.findAll();
        model.addAttribute("productSale", products);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        return "home/product";
    }
    @GetMapping("/categories/{id}")
    public String getProductByCategoryId(@PathVariable(value = "id") long id,Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Product> products = productRepository.findProductsByCategoryId(id);
        model.addAttribute("productByCategoryId", products);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        return "home/category";
    }
    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable(value = "id") long id, Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Product product = productService.getProductById(id);
        model.addAttribute("product",product);
        List<Product> products = productRepository.findProductSale();
        model.addAttribute("productSale", products);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        return "home/productDetail";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Product> products = productRepository.findProductSale();
        model.addAttribute("productSale", products);
        int Count = shoppingCartService.getCount();
        model.addAttribute("count",Count);
        return "home/contact"  ;
    }


}
