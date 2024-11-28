package com.code.shop.service.product;

import com.code.shop.model.Product;
import com.code.shop.request.ProductAddRequest;
import com.code.shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);


    Product getProductById(Long id);

    Product addProduct(ProductAddRequest product);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    void deleteProduct(Long id);

    Long countProductsByBrandAndName(String brand, String name);
}
