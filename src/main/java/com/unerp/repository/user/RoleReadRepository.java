package com.unerp.repository.user;

import com.unerp.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReadRepository extends JpaRepository <Role, Integer>{

    Role findByName(String name);
}
