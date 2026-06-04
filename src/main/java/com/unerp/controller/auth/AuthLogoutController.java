package com.unerp.controller.auth;

import com.unerp.service.auth.ActiveSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthLogoutController {

    private final ActiveSessionService activeSessionService;

    public AuthLogoutController(ActiveSessionService activeSessionService) {
        this.activeSessionService = activeSessionService;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        activeSessionService.clearSession();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logout Exitoso");

    }
}
