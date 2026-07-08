package com.unerp.service.user;


import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.user.User;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class UserGetByIdService {
  private final UserReadRepository userReadRepository;
  private final AuthorizationService authorizationService;

  public UserGetByIdService(
      UserReadRepository userReadRepository,
      AuthorizationService authorizationService
  ) {
    this.userReadRepository = userReadRepository;
    this.authorizationService = authorizationService;
  }

  public User getUserById(Integer userId) {
    authorizationService.validatePermission(PermissionName.GESTION_ROLES);
    return userReadRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }
}
