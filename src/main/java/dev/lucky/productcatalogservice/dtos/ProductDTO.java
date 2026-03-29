package dev.lucky.productcatalogservice.dtos;

import dev.lucky.productcatalogservice.models.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Long    id;
    private String  name;
    private String  description;
    private Double  price;
    private CategoryDTO category;
    private String  imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, CategoryDTO category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Product convertToProduct() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.category != null ? this.category.convertToCategory() : null,
                this.imageUrl
        );
    }
}
