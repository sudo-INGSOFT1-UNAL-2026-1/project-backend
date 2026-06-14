package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.user.User;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserGetAllService {

  private final UserReadRepository userReadRepository;
  private final AuthorizationService authorizationService;

  public UserGetAllService(
      UserReadRepository userReadRepository, AuthorizationService authorizationService) {
    this.userReadRepository = userReadRepository;
    this.authorizationService = authorizationService;
  }

  public List<User> getAllUsers() {
    authorizationService.validatePermission(PermissionName.GESTION_ROLES);
    return userReadRepository.findAll();
  }
}
