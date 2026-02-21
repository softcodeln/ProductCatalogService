package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.repositories.CategoryRepository;
import dev.lucky.productcatalogservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("StorageProductService")
@AllArgsConstructor
public class StorageProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
}
