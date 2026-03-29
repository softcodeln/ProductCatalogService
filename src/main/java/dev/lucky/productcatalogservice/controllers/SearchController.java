package dev.lucky.productcatalogservice.controllers;

import dev.lucky.productcatalogservice.dtos.ProductDTO;
import dev.lucky.productcatalogservice.dtos.SearchRequestDto;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.services.ISearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final ISearchService searchService;

    @PostMapping
    public Page<ProductDTO> searchProduct(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<Product> products = searchService.searchProduct(
                searchRequestDto.getQuery(),
                searchRequestDto.getPageNo(),
                searchRequestDto.getPageSize(),
                searchRequestDto.getSortParams()
        );

        return products.map(Product::convertToDto);
    }
}
