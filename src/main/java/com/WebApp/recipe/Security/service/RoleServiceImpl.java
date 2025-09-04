package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.Role;
import com.WebApp.recipe.Security.repository.RoleRepository;
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
