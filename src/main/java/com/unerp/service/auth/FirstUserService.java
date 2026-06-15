package com.unerp.service.auth;

import com.unerp.repository.user.UserReadRepository;
import org.springframework.stereotype.Service;

@Service
public class FirstUserService {

  private final UserReadRepository userReadRepository;

  public FirstUserService(UserReadRepository userReadRepository) {
    this.userReadRepository = userReadRepository;
  }

  public boolean isFirstUser() {
    return userReadRepository.count() == 0;
  }
}
