package dev.lucky.productcatalogservice.models;

import dev.lucky.productcatalogservice.dtos.CategoryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel{
    /*
    name: String
    description: String
    products: List<Product>
    */
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public CategoryDTO convertToDto() {
        return new CategoryDTO(this.getId(), this.getName(), this.getDescription());
    }
}
