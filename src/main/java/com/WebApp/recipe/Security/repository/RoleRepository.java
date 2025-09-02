package com.WebApp.recipe.Security.repository;

import com.WebApp.recipe.Security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole(String roleName);
}
