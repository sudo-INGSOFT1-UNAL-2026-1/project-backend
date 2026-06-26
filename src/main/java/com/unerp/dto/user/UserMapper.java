package com.unerp.dto.user;

import com.unerp.domain.user.User;

public final class UserMapper {

  private UserMapper() {}

  public static UserResponse toResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getState().getName(),
        user.getRole().getName()
    );
  }

}
