package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.Entities.UserEntities.Role;
import com.WebApp.recipe.Repositories.UserRepositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByRoleName(String name) {
        return roleRepository.findRoleByRole(name);
    }
}
