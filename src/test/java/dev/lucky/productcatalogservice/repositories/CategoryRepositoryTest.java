package dev.lucky.productcatalogservice.repositories;

import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void NplusOneProblemTest() {
        System.out.println("NplusOneProblemTest started");
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            System.out.println("Category: " + category.getName());
            for (Product product : category.getProducts()) {
                System.out.println(product.getName());
            }
        }
        System.out.println("NplusOneProblemTest ended");
    }

//    @Test
//    @Transactional
//    public void testFindById() {
//        Optional<Category> category = categoryRepository.findById(11L);
//        if (category.isPresent()) {
//            Category category1 = category.get();
//            // This will trigger the SQL query for products
//            System.out.println("Number of products: " + category1.getProducts().size());
//        }
//        System.out.println();
//    }
}