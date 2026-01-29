package com.WebApp.recipe.Entities.RecipeEntities;

import com.WebApp.recipe.Entities.UserEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "instructions")
    private String instructions;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "image_public_id")
    private String imagePublicId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "users_fav_recipes",
            joinColumns = @JoinColumn (name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<RecipeIngredient> ingredients;

    public void addIngredient(RecipeIngredient ingredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public Recipe(String title, String shortDescription, String instructions, Video video, List<RecipeIngredient> ingredients) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.instructions = instructions;
        this.video = video;
        this.ingredients = ingredients;
        this.isDeleted = false;
    }

    public Recipe(String title, String shortDescription, String instructions, Video video, String imagePublicId, List<RecipeIngredient> ingredients) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.instructions = instructions;
        this.video = video;
        this.imagePublicId = imagePublicId;
        this.ingredients = ingredients;
        this.isDeleted = false;
    }

    public void deleteRecipe() {
        this.isDeleted = true;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void addUsersLike(User user) {
        if(users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public void deleteUsersLike(User user) {
        if(users != null) {
            users.remove(user);
        }
    }
}
