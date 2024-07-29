package com.example.shop.controller.admin;

import com.example.shop.model.Category;
import com.example.shop.respository.CategoryRepository;
import com.example.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("")
    public String getAllCategory(Model model){
        List<Category> category = categoryRepository.findAll();
        model.addAttribute("category", category);
        return "admin/productCategory/index";
    }
    @GetMapping("addCategory")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "admin/productCategory/addCategory";
    }
    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }
    @GetMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable(value = "id") long id, Model model){
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/productCategory/updateCategory";
    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") long id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    // API
    /*@PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        return new ResponseEntity<Category>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategory();
    }
    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id){
        return new ResponseEntity<Category>(categoryService.getCategoryById(id),HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category category){
        return new ResponseEntity<Category>(categoryService.updateCategory(category,id),HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<String>("Category deleted successfully",HttpStatus.OK);
    }*/
}
