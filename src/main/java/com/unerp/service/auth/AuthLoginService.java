package com.unerp.service.auth;

import com.unerp.domain.user.User;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.security.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class AuthLoginService {

  private final UserReadRepository userReadRepository;

  private final PasswordHasher passwordHasher;

  public AuthLoginService(UserReadRepository userReadRepository, PasswordHasher passwordHasher) {
    this.userReadRepository = userReadRepository;
    this.passwordHasher = passwordHasher;
  }

  public User login(String email, String password) {

    User user = userReadRepository.findByEmail(email);

    validateUserExists(user);

    validatePassword(password, user.getPasswordHash());

    validateActiveUser(user);

    return user;
  }

  private void validateUserExists(User user) {
    if (user == null) {
      throw new IllegalArgumentException("El usuario no existe");
    }
  }

  private void validatePassword(String password, String passwordHash) {
    if (!passwordHasher.matches(password, passwordHash)) {
      throw new IllegalArgumentException("Contraseña incorrecta");
    }
  }

  private void validateActiveUser(User user) {

    String state = user.getState().getName();

    if (!state.equals("Activo")) {
      throw new IllegalArgumentException("El usuario no esta activo");
    }
  }
}
