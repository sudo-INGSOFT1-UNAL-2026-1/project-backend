package com.unerp.domain.rolePermission;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolePermissionId implements Serializable {

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "permission_id")
    private Integer permissionId;

    protected RolePermissionId() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        RolePermissionId other = (RolePermissionId) object;

        return Objects.equals(roleId, other.roleId) && Objects.equals(permissionId, other.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);

    }
}
