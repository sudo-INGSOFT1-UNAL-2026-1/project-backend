package com.unerp.controller.user;

import com.unerp.domain.user.User;
import com.unerp.service.user.UserCreateService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCreateController {

  private final UserCreateService userCreateService;

  public UserCreateController(UserCreateService userCreateService) {
    this.userCreateService = userCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createUser(
      @RequestParam String name,
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam String roleName) {

    try {
      User user = userCreateService.createUser(name, email, password, roleName);

      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("id", user.getId());
      responseBody.put("name", user.getName());
      responseBody.put("email", user.getEmail());
      responseBody.put("state", user.getState().getName());
      responseBody.put("role", user.getRole().getName());

      return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
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
