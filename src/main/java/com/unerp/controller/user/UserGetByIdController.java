package com.unerp.controller.user;

import com.unerp.domain.user.User;
import com.unerp.dto.user.UserMapper;
import com.unerp.service.user.UserGetByIdService;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserGetByIdController {

  private final UserGetByIdService userGetByIdService;

  public UserGetByIdController(UserGetByIdService userGetByIdService) {
    this.userGetByIdService = userGetByIdService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Integer id) {
    try {
      User user = userGetByIdService.getUserById(id);
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(UserMapper.toResponse(user));
    } catch (NoSuchElementException e) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(e.getMessage());
    }
  }
}
