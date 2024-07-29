package com.example.shop.service;

import com.example.shop.model.Category;
import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategory();
    Category getCategoryById(long id);
    Category updateCategory(Category category, long id);
    void deleteCategory(long id);
}
