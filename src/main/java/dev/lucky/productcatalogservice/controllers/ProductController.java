package dev.lucky.productcatalogservice.controllers;

import dev.lucky.productcatalogservice.dtos.ProductDTO;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final IProductService productService;

    public ProductController(@Qualifier("StorageProductService") IProductService productService) {
        this.productService = productService;
    }

    /*
    1. Create product
    2. Get product by id
    3. Get all products
    */

    /*
    Create product ("/products") - POST
    Get product by id ("/products/{id}") - GET
    Get all products ("/products") - GET
    @RequestMapping("/products")
    @GetMapping
    @PostMapping
    */

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productRequestDto) {
        ProductDTO responseDto = productService.createProduct(productRequestDto.convertToProduct()).convertToDto();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Invalid product ID: " + id);
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductDTO productDto = product.convertToDto();
        return new ResponseEntity<>(productDto ,HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        List<Product> productResponse = productService.getAllProducts();

        return productResponse.stream().map(Product::convertToDto).toList();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Long productId,
                                    @RequestBody ProductDTO productDTO) {
        if (productId < 1) {
            throw new IllegalArgumentException("Invalid product ID: " + productId);
        }
        Product product = productDTO.convertToProduct();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product = productService.replaceProduct(productId, product);
        return new ResponseEntity<>(product.convertToDto(), HttpStatus.OK);
    }

}
