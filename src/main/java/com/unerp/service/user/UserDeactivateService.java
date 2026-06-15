package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.user.User;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.service.auth.ActiveSessionService;
import com.unerp.service.auth.AuthorizationService;
import com.unerp.service.auth.JwtService;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserDeactivateService {

  private final UserReadRepository userReadRepository;
  private final UserWriteRepository userWriteRepository;
  private final AuthorizationService authorizationService;
  private final JwtService jwtService;
  private final ActiveSessionService activeSessionService;

  public UserDeactivateService(
      UserReadRepository userReadRepository,
      UserWriteRepository userWriteRepository,
      AuthorizationService authorizationService,
      JwtService jwtService,
      ActiveSessionService activeSessionService) {
    this.userReadRepository = userReadRepository;
    this.userWriteRepository = userWriteRepository;
    this.authorizationService = authorizationService;
    this.jwtService = jwtService;
    this.activeSessionService = activeSessionService;
  }

  public User deactivateUser(Integer userId) {

    authorizationService.validatePermission(PermissionName.GESTION_ROLES);

    User user =
        userReadRepository
            .findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found"));

    validateNotCurrentUser(user);

    user.deactivate();

    return userWriteRepository.save(user);
  }

  private void validateNotCurrentUser(User user) {

    Integer currentUserId = jwtService.extractUserId(activeSessionService.getActiveToken());

    if (user.getId().equals(currentUserId)) {
      throw new IllegalArgumentException("No puedes desactivar tu propio usuario");
    }
  }
}
