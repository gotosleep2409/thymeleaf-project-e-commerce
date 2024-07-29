package com.example.shop.controller.admin;

import com.example.shop.StorageService;
import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.model.Product;
import com.example.shop.respository.ProductRepository;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    private StorageService storageService;
    private ProductService productService;
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String getAllProduct(Model model) {
        List<Product> product = productRepository.findAll();
        model.addAttribute("product", product);
        return "admin/product/product";
    }

    @GetMapping("addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        List<Category> listCategory = this.categoryService.getAllCategory();
        model.addAttribute("listCategory", listCategory);
        return "admin/product/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("Product") Product Product, @RequestParam("imageProduct") MultipartFile file) {
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        Product.setImage(fileName);
        productService.saveProduct(Product);

        return "redirect:/admin/products";
    }

    @GetMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> listCategory = this.categoryService.getAllCategory();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("product", product);
        return "admin/product/updateProduct";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    /*@PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("Product") Product Product, @RequestParam("imageProduct") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        boolean isEmpty = fileName == null || fileName.trim().length() == 0;
        if(!isEmpty){
            this.storageService.store(file);
            Product.setImage(fileName);
        }
        productService.saveProduct(Product);
        return "redirect:/admin/products";
    }*/
    @PostMapping("/saveProduct/{id}")
    public String saveProduct(@ModelAttribute("Product") Product Product, @RequestParam("imageProduct") MultipartFile file,@PathVariable(value = "id") long id) {
        String fileName = file.getOriginalFilename();
        boolean isEmpty = fileName == null || fileName.trim().length() == 0;
        if(!isEmpty){
            this.storageService.store(file);
            Product.setImage(fileName);
        }
        productService.updateProduct(Product,id);
        return "redirect:/admin/products";
    }

    //API
    /*@PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.CREATED);
    }
    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id){
        return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Product> updateCategory(@PathVariable("id") long id, @RequestBody Product product){
        return new ResponseEntity<Product>(productService.updateProduct(product,id), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);
    }*/
}
