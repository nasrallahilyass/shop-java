package com.code.shop.service.product;

import com.code.shop.model.Product;

import java.util.List;

public class ProductService implements IProductService{
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public void deleteProduct(Long id) {

    }
}
