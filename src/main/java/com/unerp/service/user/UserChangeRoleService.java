package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.role.Role;
import com.unerp.domain.user.User;
import com.unerp.repository.user.RoleReadRepository;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserChangeRoleService {

  private final UserReadRepository userReadRepository;
  private final UserWriteRepository userWriteRepository;
  private final RoleReadRepository roleReadRepository;
  private final AuthorizationService authorizationService;

  public UserChangeRoleService(
      UserReadRepository userReadRepository,
      UserWriteRepository userWriteRepository,
      RoleReadRepository roleReadRepository,
      AuthorizationService authorizationService) {
    this.userReadRepository = userReadRepository;
    this.userWriteRepository = userWriteRepository;
    this.roleReadRepository = roleReadRepository;
    this.authorizationService = authorizationService;
  }

  public User changeRole(Integer userId, String newRoleName) {

    authorizationService.validatePermission(PermissionName.GESTION_ROLES);

    User user =
        userReadRepository
            .findById(userId)
            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

    Role newRole = roleReadRepository.findByName(newRoleName);
    if (newRole == null) {
      throw new NoSuchElementException("Role not found");
    }

    user.setRole(newRole);

    return userWriteRepository.save(user);
  }
}
