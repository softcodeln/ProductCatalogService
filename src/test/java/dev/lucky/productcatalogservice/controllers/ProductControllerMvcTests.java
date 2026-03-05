package dev.lucky.productcatalogservice.controllers;

import dev.lucky.productcatalogservice.dtos.CategoryDTO;
import dev.lucky.productcatalogservice.dtos.ProductDTO;
import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTests {
        /*
        In this test class, we will write MockMvc tests for the ProductController class.
        We will use MockMvc to simulate HTTP requests and test the controller endpoints, request validation, response structure, and interactions with the service layer.
        We will also use Mockito to mock the IProductService dependency and verify that the controller methods are calling the service methods correctly.
        */
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Test
    public void testGetAllProductsRunSuccessfully() throws Exception {
        Product product1 = new Product(1L,
                "Test Product 1",
                "This is a test product 1",
                9.99,
                new Category("Test Category", "This is a test category", null),
                "http://example.com/image1.jpg"
        );
        List<Product> products = List.of(product1);

        // Mock the productService to return the products when getAllProducts is called.
        when(productService.getAllProducts()).thenReturn(products);

        ProductDTO productDTO = new ProductDTO(1L,
                "Test Product 1",
                "This is a test product 1",
                9.99,
                new CategoryDTO(null, "Test Category","This is a test category"),
                "http://example.com/image1.jpg"
        );

        List<ProductDTO> expectedResponse = List.of(productDTO);
        ObjectMapper mapper = new ObjectMapper();
        String jsonExpectedResponse = mapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(content().json(jsonExpectedResponse));
    }
}

/*
MockMvc tests are a way to test your Spring MVC controllers
without starting the full server.

They simulate HTTP requests (GET, POST, PUT…) and let you test:
- Controller endpoints
- Request validation
- Request/response structure
- HTTP status codes
- JSON serialization/deserialization
- Interactions with services (via mocks)

They are fast, lightweight, and focused.

 */