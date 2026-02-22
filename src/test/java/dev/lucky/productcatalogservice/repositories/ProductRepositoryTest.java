package dev.lucky.productcatalogservice.repositories;

import dev.lucky.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testJpaMethods() {
        List<Product> products = productRepository.findProductByPriceBetween(10.0, 1000.0);
        products = productRepository.findAllByOrderByPrice();
        String productDescription = productRepository.getDescriptionWhereIdIs(5L);
        System.out.println(products);
    }
}