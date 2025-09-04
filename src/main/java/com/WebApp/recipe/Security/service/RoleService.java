package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.Role;

public interface RoleService {
    Role getRoleByRoleName(String name);
}
