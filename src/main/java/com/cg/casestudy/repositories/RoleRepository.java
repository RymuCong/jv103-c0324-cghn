package com.cg.casestudy.repositories;

import com.cg.casestudy.models.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleByName(String name);
}
