package com.code.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.code.shop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
