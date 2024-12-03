package com.code.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.code.shop.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);

    boolean existsByName(String name);
}
