package com.unerp.controller.auth;

import com.unerp.domain.user.User;
import com.unerp.dto.auth.LoginMapper;
import com.unerp.dto.auth.LoginRequest;
import com.unerp.dto.auth.LoginResponse;
import com.unerp.service.auth.ActiveSessionService;
import com.unerp.service.auth.AuthLoginService;
import com.unerp.service.auth.JwtService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthLoginController {

  private final AuthLoginService authLoginService;
  private final JwtService jwtService;
  private final ActiveSessionService activeSessionService;

  public AuthLoginController(
      AuthLoginService authLoginService,
      JwtService jwtService,
      ActiveSessionService activeSessionService) {
    this.authLoginService = authLoginService;
    this.jwtService = jwtService;
    this.activeSessionService = activeSessionService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    try {

      activeSessionService.validateNoActiveSession();

      User user = authLoginService.login(request.email(), request.password());

      String token = jwtService.generateToken(user);

      activeSessionService.setActiveToken(token);

      return ResponseEntity.status(HttpStatus.OK).body(LoginMapper.toResponse(token, user));
    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
