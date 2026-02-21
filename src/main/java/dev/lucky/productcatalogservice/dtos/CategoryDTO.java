package dev.lucky.productcatalogservice.dtos;

import dev.lucky.productcatalogservice.models.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    public CategoryDTO(String name) {
        this.name = name;
    }

    public Category convertToCategory() {
        return new Category(this.name, this.description, null);
    }
}
