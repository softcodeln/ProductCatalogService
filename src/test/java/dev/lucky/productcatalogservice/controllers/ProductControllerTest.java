package dev.lucky.productcatalogservice.controllers;

import dev.lucky.productcatalogservice.dtos.ProductDTO;
import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {
    /* Steps to create a UT's ::
     Step 1. Arrange
     Step 2. Act
     Step 3. Assert
     Step 4. Verify (optional)
    */

    /*
    This test class will contain unit tests for the ProductController class.
     We will use Mockito to mock the IProductService dependency and test the controller methods in isolation.
    */
    @Autowired
    private ProductController productController;
    @MockitoBean
    private IProductService productService;

    //Happy Path Scenario
    // unit test for getProductById method, and we will consider Happy path scenario only in this test method.
    @Test
    public void testGetProductByIdWithValidIdRunSuccessfully() {
        Product product = new Product(1L,
                "Test Product",
                "This is a test product",
                9.99,
                new Category("Test Category", "This is a test category", null),
                "http://example.com/image.jpg"
        );
        // Mock the productService to return the product when getProductById is called with id 1L
        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<ProductDTO> productResponseDto = productController.getProductById(1L);

        assertEquals(200, productResponseDto.getStatusCode().value());
        assertNotNull(productResponseDto.getBody());
        assertEquals(product.getId(), productResponseDto.getBody().getId());
        assertEquals(product.getName(), productResponseDto.getBody().getName());
        assertEquals(product.getDescription(), productResponseDto.getBody().getDescription());
        assertEquals(product.getPrice(), productResponseDto.getBody().getPrice());
        assertEquals(product.getImageUrl(), productResponseDto.getBody().getImageUrl());

        verify(productService, times(1)).getProductById(1L);
    }

    // Sad Path Scenario
    // unit test for getProductById method, and we will consider Sad path scenario only in this test method.
    @Test
    public void testGetProductByIdWithInvalidIdThrowException() {
        Long invalidId = -1L;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.getProductById(invalidId);
        });

        assertEquals("Invalid product ID: " + invalidId, exception.getMessage());

        verify(productService, times(0)).getProductById(anyLong());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.getProductById(0L);
        });
        assertEquals("Product exist with positive Id's only.", exception.getMessage());

        verify(productService, times(0)).getProductById(anyLong());
    }
}