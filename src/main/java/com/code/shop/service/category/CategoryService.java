package com.code.shop.service.category;

import com.code.shop.exceptions.NotFoundException;
import com.code.shop.exceptions.AlreadyExistException;
import com.code.shop.model.Category;
import com.code.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    // Get Categories METHODS:
    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    // CRUD Operations
    // Add, Update, Delete Products Methods
    @Override
    public Category addCategory(Category category) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByName(category.getName()));
        if (categoryOptional.isPresent()) {
            throw new AlreadyExistException(category.getName() + " already exists");
        } else {
            return categoryRepository.save(category);
        }
    }

    @Override
    public Category updateCategory(Category category, String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category categoryToUpdate = categoryOptional.get();
            categoryToUpdate.setName(category.getName());
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.findById(id).
                ifPresentOrElse(categoryRepository::delete,
                        () -> {
                            throw new NotFoundException("Category not found");
                        });
    }

}
