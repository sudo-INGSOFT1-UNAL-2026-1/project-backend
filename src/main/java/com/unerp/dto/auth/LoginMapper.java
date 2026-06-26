package com.unerp.dto.auth;

import com.unerp.domain.user.User;

public final class LoginMapper {

  private LoginMapper() {}

  public static LoginResponse toResponse(String token, User user) {
    return new LoginResponse(
        token,
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getState().getName(),
        user.getRole().getName()
    );
  }
}
