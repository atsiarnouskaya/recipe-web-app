package com.WebApp.recipe.DTOs.CategoryDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z_-]+$")
    private String categoryName;

}
