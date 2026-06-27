package com.unerp.service.auth;

import com.unerp.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SECRET_KEY = "unerp-secret-key-development-unerp";

  private static final Duration TOKEN_DURATION = Duration.ofHours(2);

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(User user) {
    return Jwts.builder()
        .subject(user.getEmail())
        .claim("userId", user.getId())
        .claim("roleId", user.getRole().getId())
        .claim("roleName", user.getRole().getName())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + TOKEN_DURATION.toMillis()))
        .signWith(getSigningKey())
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean isTokenValid(String token) {
    try {
      extractAllClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).getSubject();
  }

  public Integer extractUserId(String token) {
    return extractAllClaims(token).get("userId", Integer.class);
  }

  public Integer extractRoleId(String token) {
    return extractAllClaims(token).get("roleId", Integer.class);
  }

  public String extractRoleName(String token) {
    return extractAllClaims(token).get("roleName", String.class);
  }
}
