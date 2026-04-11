package dev.lucky.productcatalogservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.lucky.productcatalogservice.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
}
