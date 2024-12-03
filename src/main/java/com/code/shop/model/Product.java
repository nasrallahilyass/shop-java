package com.code.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")  // MongoDB collection name
@JsonIgnoreProperties({"category", "images"}) // Ignore these fields in JSON serialization
public class Product {

    @Id
    private String id;  // MongoDB uses String type for IDs

    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    // Many products belong to one category (DBRef instead of @ManyToOne)
    @DBRef
    private Category category;

    // One product can have many images (List of embedded documents or DBRef)
    @DBRef(lazy = false)
    private List<Image> images;

    // Constructor for easy creation
    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
