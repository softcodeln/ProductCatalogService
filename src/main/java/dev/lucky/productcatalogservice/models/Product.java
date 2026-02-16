package dev.lucky.productcatalogservice.models;

import dev.lucky.productcatalogservice.dtos.CategoryDTO;
import dev.lucky.productcatalogservice.dtos.FakeStoreProductDTO;
import dev.lucky.productcatalogservice.dtos.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel{
    /*
    name: String
    description: String
    price: Double
    category: Category
    imageUrl: String
    */
    private String name;
    private String description;
    private Double price;
    @ManyToOne
    private Category category;
    private String imageUrl;

    public Product(Long id,String name, String description, Double price, Category category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public ProductDTO convertToDto() {
        return new ProductDTO(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.getCategory() != null ? new CategoryDTO(this.getCategory().getName()) : null,
                this.getImageUrl()
        );
    }

    public FakeStoreProductDTO convertToFakeProductDto(){
        return new FakeStoreProductDTO(this.getId(),
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.getCategory().getName(),
                this.getImageUrl());
    }
}
