package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.dtos.UserDto;
import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.repositories.CategoryRepository;
import dev.lucky.productcatalogservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("StorageProductService")
@AllArgsConstructor
@Primary
public class StorageProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestTemplate restTemplate;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public Product createProduct(Product product) {
        // Handle category: if category is null or category name is null or blank, throw exception else find category by name and set it to product, if category not found throw exception.
        Category category = handleCategory(product.getCategory());
        product.setCategory(category);
        return productRepository.save(product);
    }

    private Category handleCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return categoryRepository.findByNameIgnoreCase(category.getName()).orElseThrow(() -> new IllegalArgumentException("Category with name " + category.getName() + " not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return Optional.ofNullable(productRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalStateException("No products found"));
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product getProductBaseOnUserScope(Long productId, Long userId) {
        Optional<Product> optionalProduct = productRepository.getProductsById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with id " + productId + " not found");
        }

        try {
            UserDto userDto = restTemplate.getForObject(
                    "http://USERAUTHSERVICE:8080/users/{userId}",
                    UserDto.class,
                    userId
            );

            if (userDto == null) {
                throw new IllegalArgumentException("User with id " + userId + " not found");
            }
        } catch (RestClientException e) {
            throw new IllegalArgumentException("Failed to fetch user from UserAuth service for userId=" + userId + ": " + e.getMessage(), e);
        }

        return optionalProduct.get();
    }

}
