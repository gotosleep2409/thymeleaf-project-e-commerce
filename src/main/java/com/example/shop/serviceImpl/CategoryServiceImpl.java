package com.example.shop.serviceImpl;

import com.example.shop.model.Category;
import com.example.shop.respository.CategoryRepository;
import com.example.shop.service.CategoryService;
import org.springframework.stereotype.Service;
import com.example.shop.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category","Id",id));
    }
    @Override
    public Category updateCategory(Category category, long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category","Id",id));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }
    @Override
    public void deleteCategory(long id) {
        categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category","Id",id));
        categoryRepository.deleteById(id);

    }
}
