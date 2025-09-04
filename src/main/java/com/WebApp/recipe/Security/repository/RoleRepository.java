package com.WebApp.recipe.Security.repository;

import com.WebApp.recipe.Security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole(String roleName);
}
