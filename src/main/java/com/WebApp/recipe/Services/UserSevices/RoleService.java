package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.Entities.UserEntities.Role;

public interface RoleService {
    Role getRoleByRoleName(String name);
}
