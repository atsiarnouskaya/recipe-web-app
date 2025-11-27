package com.WebApp.recipe.Security.entity;

import com.WebApp.recipe.entity.Recipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    public User(boolean enabled, String username, String password) {
        this.enabled = enabled;
        this.username = username;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                       CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    @ManyToMany(mappedBy = "users")
    List<Recipe> favoriteRecipes;

    public void addFavoriteRecipe(Recipe recipe) {
        if (this.favoriteRecipes == null) {
            this.favoriteRecipes = new ArrayList<>();
        }
        this.favoriteRecipes.add(recipe);
    }

}
