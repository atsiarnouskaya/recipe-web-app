package com.WebApp.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipes_ingredients")
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;

    @MapsId("recipeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, Double amount, Unit unit) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
        this.id = new RecipeIngredientId(recipe.getId(), ingredient.getId());
    }
}