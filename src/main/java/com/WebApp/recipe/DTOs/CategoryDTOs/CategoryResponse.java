package com.WebApp.recipe.DTOs.CategoryDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private String categoryName;
    private List<String> ingredients;

    public CategoryResponse(String categoryName) {
        this.categoryName = categoryName;
    }
}
