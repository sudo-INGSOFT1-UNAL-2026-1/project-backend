package com.unerp.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

    private final BCryptPasswordEncoder enconder =
            new BCryptPasswordEncoder();

    public String hash(String password) {
        return enconder.encode(password);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return enconder.matches(rawPassword, hashedPassword);
    }
}
