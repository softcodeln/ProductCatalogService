package dev.lucky.productcatalogservice.services;

import dev.lucky.productcatalogservice.clients.FakeStoreAPIClient;
import dev.lucky.productcatalogservice.dtos.FakeStoreProductDTO;
import dev.lucky.productcatalogservice.models.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements IProductService{
    private final RestTemplate restTemplate;
    private final FakeStoreAPIClient fakeStoreAPIClient;

    public FakeStoreProductService(RestTemplate restTemplate, FakeStoreAPIClient fakeStoreAPIClient) {
        this.restTemplate = restTemplate;
        this.fakeStoreAPIClient = fakeStoreAPIClient;
    }

    public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request,
                                              Class<T> responseType, @Nullable Object... uriVariables) throws RestClientException {

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public Product getProductById(Long id) {
//        FakeStoreProductDTO fakeStoreProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductDTO.class, id);
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity = fakeStoreAPIClient.requestForEntity(
                HttpMethod.GET,
                "https://fakestoreapi.com/products/{id}",
                null,
                FakeStoreProductDTO.class,
                id);
        if (fakeStoreAPIClient.validateResponse(fakeStoreProductDTOResponseEntity)) {
            return fakeStoreProductDTOResponseEntity.getBody().convertToProduct();
        }
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return product;
//        FakeStoreProductDTO fakeProduct = new FakeStoreProductDTO(product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getCategory().getName(),
//                product.getImageUrl()
//        );
//        ResponseEntity<FakeStoreProductDTO> response = fakeStoreAPIClient.requestForEntity(
//                HttpMethod.POST,
//                "http://fakestoreapi.com/products",
//                fakeProduct,
//                FakeStoreProductDTO.class
//        );
//        if (fakeStoreAPIClient.validateResponse(response)) {
//            return response.getBody().convertToProduct();
//        }
//        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        // Use array type with getForObject - arrays preserve generic type information
        ResponseEntity<FakeStoreProductDTO[]> response = fakeStoreAPIClient.requestForEntity(
                HttpMethod.GET,
                "https://fakestoreapi.com/products",
                null,
                FakeStoreProductDTO[].class
        );

        if (fakeStoreAPIClient.validateResponse(response)) {
            FakeStoreProductDTO[] fakeStoreProductDtoArray = response.getBody();
            return Stream.of(fakeStoreProductDtoArray)
                    .map(FakeStoreProductDTO::convertToProduct)
                    .toList();
        }
        return List.of();
    }

    /*
    PUT - replaces/updates the entire product
    PATCH - partially updates the product
    DELETE - deletes the product
    * */

    @Override
    public Product replaceProduct(Long id, Product product){
        FakeStoreProductDTO fakeStoreProductDTO = product.convertToFakeProductDto();
        ResponseEntity<FakeStoreProductDTO> response = fakeStoreAPIClient.requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDTO,
                FakeStoreProductDTO.class,
                id
                );

        if (fakeStoreAPIClient.validateResponse(response)) {
            return response.getBody().convertToProduct();
        }
        return null;
    }

}
