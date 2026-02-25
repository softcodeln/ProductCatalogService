package dev.lucky.productcatalogservice.models;

import dev.lucky.productcatalogservice.dtos.CategoryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public CategoryDTO convertToDto() {
        return new CategoryDTO(this.getId(), this.getName(), this.getDescription());
    }
}
