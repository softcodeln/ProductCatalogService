package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product replaceProduct(Long id, Product product);
}
