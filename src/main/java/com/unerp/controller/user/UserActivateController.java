package com.unerp.controller.user;

import com.unerp.dto.user.UserIdRequest;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.user.User;
import com.unerp.dto.user.UserMapper;
import com.unerp.service.user.UserActivateService;

@RestController
@RequestMapping("/user")
public class UserActivateController {

  private final UserActivateService userActivateService;

  public UserActivateController(UserActivateService userActivateService) {
    this.userActivateService = userActivateService;
  }

  @PutMapping("/activate")
  public ResponseEntity<?> activateUser(@Valid @RequestBody UserIdRequest request) {
    try {
      User user = userActivateService.activateUser(request.userId());

      return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponse(user));
    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (NoSuchElementException e) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
