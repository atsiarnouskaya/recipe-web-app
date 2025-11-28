package com.WebApp.recipe.dto.IngredientDTOs;

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
public class IngredientRequest {
    private String recipeName;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Length must be between 1 and 50")
    @Pattern(
            regexp = "^(?!.* {2,})['!?a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String ingredientName;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Length must be between 1 and 50")
    @Pattern(
            regexp = "^(?!.* {2,})['!?a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String categoryName;

    @NotBlank(message = "Cannot be blank")
    @Pattern(
            regexp = "^[0-9]*.[0-9]+$",
            message = "Some symbol you provided is not allowed"
    )
    private Double amount;

    private String unit;
}
