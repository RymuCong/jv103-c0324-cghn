package com.cg.casestudy.services.impl;

import com.cg.casestudy.models.user.Role;
import com.cg.casestudy.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}
