package com.WebApp.recipe.Repositories.UserRepositories;

import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Entities.RecipeEntities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT r.* FROM recipes r " +
                    "JOIN users_fav_recipes ufr ON r.id = ufr.recipe_id " +
                    "WHERE user_id = :userId", nativeQuery = true)
    List<Recipe> findRecipesByUserId(int userId);
}
