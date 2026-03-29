package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.dtos.SortParam;
import dev.lucky.productcatalogservice.models.Product;
import dev.lucky.productcatalogservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService {
    private final ProductRepository productRepository;

    @Override
    public Page<Product> searchProduct(String query, Integer pageNo, Integer pageSize, List<SortParam>  sortParams) {
        Sort sort = sortParams.isEmpty()
                ? Sort.unsorted()
                : Sort.by(sortParams.stream()
                .map(sortParam -> new Sort.Order(
                        "desc".equalsIgnoreCase(sortParam.getOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC,
                        sortParam.getParamName()
                ))
                .toList());

        return productRepository.findByName(query, PageRequest.of(pageNo, pageSize, sort));
    }
}
