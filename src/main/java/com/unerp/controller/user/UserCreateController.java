package com.unerp.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.user.User;
import com.unerp.dto.user.CreateUserRequest;
import com.unerp.dto.user.UserMapper;
import com.unerp.service.user.UserCreateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserCreateController {

  private final UserCreateService userCreateService;

  public UserCreateController(UserCreateService userCreateService) {
    this.userCreateService = userCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createUser(
      @Valid @RequestBody CreateUserRequest request
      ){

    try {
      User user = userCreateService.createUser(
          request.name(),
          request.email(),
          request.password(),
          request.roleName()
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(user));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
