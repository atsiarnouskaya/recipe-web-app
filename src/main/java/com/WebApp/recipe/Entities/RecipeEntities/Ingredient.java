package com.WebApp.recipe.Entities.RecipeEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "ingredient",
               cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                       CascadeType.REFRESH, CascadeType.DETACH},
               fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipes;

    public void addRecipe(RecipeIngredient recipe) {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }
        recipes.add(recipe);
    }

    public Ingredient(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void deleteIngredient() {
        this.isDeleted = true;
    }
}
