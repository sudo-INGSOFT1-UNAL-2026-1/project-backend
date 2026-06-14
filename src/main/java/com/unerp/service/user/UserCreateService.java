package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.role.Role;
import com.unerp.domain.role.RoleName;
import com.unerp.domain.user.User;
import com.unerp.domain.user.UserBuilder;
import com.unerp.domain.user.state.ActiveState;
import com.unerp.repository.user.RoleReadRepository;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.security.PasswordHasher;
import com.unerp.service.auth.AuthorizationService;
import com.unerp.service.auth.FirstUserService;
import org.springframework.stereotype.Service;

@Service
public class UserCreateService {

  private final UserReadRepository userReadRepository;
  private final UserWriteRepository userWriteRepository;
  private final RoleReadRepository roleReadRepository;
  private final PasswordHasher passwordHasher;
  private final AuthorizationService authorizationService;
  private final FirstUserService firstUserService;

  public UserCreateService(
      UserReadRepository userReadRepository,
      UserWriteRepository userWriteRepository,
      RoleReadRepository roleReadRepository,
      PasswordHasher passwordHasher,
      AuthorizationService authorizationService,
      FirstUserService firstUserService) {
    this.userReadRepository = userReadRepository;
    this.userWriteRepository = userWriteRepository;
    this.passwordHasher = passwordHasher;
    this.roleReadRepository = roleReadRepository;
    this.authorizationService = authorizationService;
    this.firstUserService = firstUserService;
  }

  public User createUser(String name, String email, String password, String roleName) {
    validateEmail(email);
    validateName(name);

    validateAvailableEmail(email);

    Role role;

    if (firstUserService.isFirstUser()) {
      role = getRole(RoleName.ADMIN_EMPRESA);
    } else {
      authorizationService.validatePermission(PermissionName.GESTION_ROLES);
      role = getRole(roleName);
    }

    String passwordHash = passwordHasher.hash(password);

    User newUser =
        new UserBuilder()
            .setName(name)
            .setEmail(email)
            .setPasswordHash(passwordHash)
            .setState(new ActiveState())
            .setRole(role)
            .build();

    return userWriteRepository.save(newUser);
  }

  private void validateAvailableEmail(String email) {
    if (userReadRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("El email ya está registrado");
    }
  }

  private Role getRole(String roleName) {

    Role role = roleReadRepository.findByName(roleName);
    if (role == null) {
      throw new IllegalArgumentException("El rol especificado no existe");
    }
    return role;
  }

  private void validateName(String name) {

    String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]{2,50}$";

    if (!name.matches(regex)) {
      throw new IllegalArgumentException(
          "El nombre debe contener solo letras y espacios, y tener entre 2 y 50 caracteres.");
    }
  }

  private void validateEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    if (!email.matches(regex)) {
      throw new IllegalArgumentException("El email no tiene un formato válido.");
    }
  }
}
