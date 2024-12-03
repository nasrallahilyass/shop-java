package com.code.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String name;

    // One category can have many products (DBRef instead of @OneToMany)
    private List<Product> products;

    // Constructor for easy creation
    public Category(String name) {
        this.name = name;
    }
}
