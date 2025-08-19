package com.WebApp.recipe.dto.IngredientDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private String recipeName;
    private String ingredientName;
    private String categoryName;
    private Double adjustedAmount;
    private String convertedUnit;
}
