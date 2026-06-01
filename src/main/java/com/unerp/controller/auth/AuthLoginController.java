package com.unerp.controller.auth;

import com.unerp.service.auth.AuthLoginService;
import com.unerp.domain.user.User;
import com.unerp.service.auth.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthLoginController {

    private final AuthLoginService authLoginService;
    private final JwtService jwtService;

    public AuthLoginController (AuthLoginService authLoginService, JwtService jwtService) {
        this.authLoginService = authLoginService;
        this.jwtService = jwtService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password
    ){
        try {
            User user = authLoginService.login(email, password);

            String token = jwtService.generateToken(user);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("id", user.getId());
            responseBody.put("name", user.getName());
            responseBody.put("email", user.getEmail());
            responseBody.put("state", user.getState().getName());
            responseBody.put("role", user.getRole().getName());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
