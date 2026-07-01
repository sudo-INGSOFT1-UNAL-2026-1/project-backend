package com.unerp.controller.auth;

import com.unerp.dto.auth.InitializationResponse;
import com.unerp.service.auth.FirstUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class InitializationController {

  private final FirstUserService firstUserService;

  public InitializationController(FirstUserService firstUserService) {
    this.firstUserService = firstUserService;
  }

  @GetMapping("/is-initialized")
  public ResponseEntity<?> checkInitializationStatus() {

    return ResponseEntity.status(HttpStatus.OK)
        .body(new InitializationResponse(!firstUserService.isFirstUser()));
  }
}
