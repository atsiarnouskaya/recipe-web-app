package com.WebApp.recipe.DTOs.RecipeDTOs;

import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Length must be between 1 and 50")
    @Pattern(
            regexp = "^(?!.* {2,})['!?a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String title;


    @Size(max = 50, message = "Length must be between 1 and 50")
    @Pattern(
            regexp = "^(?!.* {2,})[a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String username;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Length must be between 1 and 200")
    @Pattern(
            regexp = "^(?!.* {2,})['!?a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String shortDescription;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 2000, message = "Length must be between 1 and 2000")
    @Pattern(
            regexp = "^(?!.* {2,})['!?’()“”–\\-\\\\/a-zA-Z0-9\\s,.-]+$",
            message = "Some symbol you provided is not allowed"
    )
    private String steps;

    @Size(min = 1, message = "Add at least one ingredient")
    private List<IngredientRequest> ingredients;

    @Size(max = 50, message = "Length must be less than 50")
    @Pattern(regexp = "(https://www\\.youtube\\.com/watch\\?v=[a-zA-Z0-9_-]{11})?", message = "Invalid youtube link")
    private String videoURL;

}
