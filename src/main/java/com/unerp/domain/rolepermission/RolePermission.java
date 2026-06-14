package com.unerp.domain.rolepermission;

import com.unerp.domain.permission.Permission;
import com.unerp.domain.role.Role;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permission")
public class RolePermission {

  @EmbeddedId private RolePermissionId id;

  @ManyToOne(optional = false)
  @MapsId("roleId")
  @JoinColumn(name = "role_id")
  private Role role;

  @ManyToOne(optional = false)
  @MapsId("permissionId")
  @JoinColumn(name = "permission_id")
  private Permission permission;

  protected RolePermission() {}

  public Role getRole() {
    return role;
  }

  public Permission getPermission() {
    return permission;
  }
}
