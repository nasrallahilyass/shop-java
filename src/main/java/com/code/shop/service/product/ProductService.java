package com.code.shop.service.product;

import com.code.shop.exceptions.NotFoundException;
import com.code.shop.model.Category;
import com.code.shop.model.Image;
import com.code.shop.model.Product;
import com.code.shop.repository.CategoryRepository;
import com.code.shop.repository.ProductRepository;
import com.code.shop.request.ProductAddRequest;
import com.code.shop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService implements IProductService {
    /*
       The @RequiredArgsConstructor annotation is provided by Lombok.
       It automatically generates a constructor for the class with parameters for
       all fields that are marked as `final` or are `@NonNull`.

       This means that Lombok will create a constructor that will initialize
       the `productRepository` field, which is required for the class to work properly.

       The `final` keyword is used to ensure that the field can only be assigned once,
       typically in the constructor, and its value cannot be changed later in the code.
       This helps enforce immutability for the field, making sure that the
       `productRepository` is properly initialized when the `ProductService` object is created.
   */
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    // Get Products + count products by brand and name METHODS
    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }


    // CRUD Operations
    // Add, Update, Delete Products Methods
    @Override
    public Product addProduct(ProductAddRequest request) {
        // Check if the category exists in the DB:
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, String productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> productRepository.save(updateExistingProduct(existingProduct, product)))
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new NotFoundException("Product not found");
                        });
    }

    public void updateProductImages(String productId, List<Image> images) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        product.setImages(images);
        productRepository.save(product);
    }

    // Helper Methods
    private Product createProduct(ProductAddRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }
}