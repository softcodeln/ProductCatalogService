package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.dtos.SortParam;
import dev.lucky.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {
    public Page<Product> searchProduct(String query, Integer pageNo, Integer pageSize, List<SortParam> sortParams);
}
