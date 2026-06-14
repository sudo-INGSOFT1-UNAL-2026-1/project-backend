package com.unerp.controller.user;

import com.unerp.domain.user.User;
import com.unerp.service.user.UserGetAllService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserGetAllController {

  private final UserGetAllService userGetAllService;

  public UserGetAllController(UserGetAllService userGetAllService) {
    this.userGetAllService = userGetAllService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllUsers() {
    try {

      List<User> users = userGetAllService.getAllUsers();

      List<Map<String, Object>> responseBody = new ArrayList<>();

      for (User user : users) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("State", user.getState().getName());
        userData.put("Role", user.getRole().getName());
        responseBody.add(userData);
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

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
