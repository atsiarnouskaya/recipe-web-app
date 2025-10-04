package com.WebApp.recipe.dto.IngredientDTOs;

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
    private String ingredientName;
    private String categoryName;
    private Double amount;
    private String unit;
//    private String startUnit;
//    private String endUnit;
    private double adjustingFactor;
}
