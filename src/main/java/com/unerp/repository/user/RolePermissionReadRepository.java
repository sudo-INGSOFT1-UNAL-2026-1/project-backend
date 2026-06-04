package com.unerp.repository.user;

import com.unerp.domain.rolePermission.RolePermission;
import com.unerp.domain.rolePermission.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionReadRepository extends JpaRepository<RolePermission, RolePermissionId> {

    boolean existsByRole_IdAndPermission_name(Integer roleId, String permissionName);   
}
