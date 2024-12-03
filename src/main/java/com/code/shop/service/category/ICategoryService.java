package com.code.shop.service.category;

import com.code.shop.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(String id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category, String id);

    void deleteCategoryById(String id);

}
