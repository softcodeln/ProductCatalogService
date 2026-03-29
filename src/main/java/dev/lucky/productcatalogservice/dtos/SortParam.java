package dev.lucky.productcatalogservice.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class SortParam {
    @NotBlank(message = "paramName is required")
    private String paramName;

    @NotBlank(message = "order is required")
    @Pattern(regexp = "(?i)asc|desc", message = "order must be asc or desc")
    private String order;
}
