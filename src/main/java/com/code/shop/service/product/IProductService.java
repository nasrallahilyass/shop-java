package com.code.shop.service.product;

import com.code.shop.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);


    Product getProductById(Long id);

    Product addProduct(Product product);

    void updateProduct(Product product, Long productId);

    void deleteProduct(Long id);

}
