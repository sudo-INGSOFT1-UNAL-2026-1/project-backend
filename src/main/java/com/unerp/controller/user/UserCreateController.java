package com.unerp.controller.user;

import com.unerp.domain.user.User;
import com.unerp.service.user.UserCreateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UserCreateController {

    private final UserCreateService userCreateService;

    public UserCreateController(UserCreateService userCreateService) {
        this.userCreateService = userCreateService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createService(

            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String roleName
    ) {
        try {

            User user = userCreateService.createUser(
                    name,
                    email,
                    password,
                    roleName
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user);
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
