package dev.lucky.productcatalogservice.dtos;

import dev.lucky.productcatalogservice.models.Category;
import dev.lucky.productcatalogservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;
    private String image;

    public FakeStoreProductDTO(Long id, String title, String description, Double price, String category, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public Product convertToProduct() {
        return new Product(
                this.id,
                this.title,
                this.description,
                this.price,
                new Category(this.category),
                this.image
        );
    }
}
