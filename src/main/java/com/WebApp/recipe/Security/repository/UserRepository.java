package com.WebApp.recipe.Security.repository;

import com.WebApp.recipe.Security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
