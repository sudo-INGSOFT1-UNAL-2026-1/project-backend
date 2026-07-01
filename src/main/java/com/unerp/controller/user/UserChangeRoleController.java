package com.unerp.controller.user;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.user.User;
import com.unerp.dto.user.UserMapper;
import com.unerp.service.user.UserChangeRoleService;

@RestController
@RequestMapping("/user")
public class UserChangeRoleController {

  private final UserChangeRoleService userChangeRoleService;

  public UserChangeRoleController(UserChangeRoleService userChangeRoleService) {
    this.userChangeRoleService = userChangeRoleService;
  }

  @PutMapping("/change-role")
  public ResponseEntity<?> changeUserRole(
      @RequestParam Integer userId, @RequestParam String newRoleName) {

    try {
      User user = userChangeRoleService.changeRole(userId, newRoleName);

      return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toResponse(user));
    } catch (NoSuchElementException e) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
