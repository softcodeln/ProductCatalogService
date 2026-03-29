package dev.lucky.productcatalogservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchRequestDto {
    @NotBlank(message = "query is required")
    private String query;

    @NotNull(message = "pageNo is required")
    @Min(value = 0, message = "pageNo must be 0 or more")
    private Integer pageNo;

    @NotNull(message = "pageSize is required")
    @Positive(message = "pageSize must be greater than 0")
    private Integer pageSize;

    @NotNull(message = "sortParams is required")
    @NotEmpty(message = "sortParams cannot be empty")
    @Valid
    private List<SortParam> sortParams = new ArrayList<>();
}
