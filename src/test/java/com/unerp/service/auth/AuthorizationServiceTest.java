package com.unerp.service.auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.unerp.repository.user.RolePermissionReadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

  @Mock private RolePermissionReadRepository rolePermissionReadRepository;

  @Mock private JwtService jwtService;

  @Mock private ActiveSessionService activeSessionService;

  @InjectMocks private AuthorizationService authorizationService;

  @Test
  public void shouldValidatePermissionSuccessfully() {

    when(activeSessionService.getActiveToken()).thenReturn("token");

    when(jwtService.extractRoleId("token")).thenReturn(1);

    when(rolePermissionReadRepository.existsByRole_IdAndPermission_name(1, "GESTION_ROLES"))
        .thenReturn(true);

    assertDoesNotThrow(() -> authorizationService.validatePermission("GESTION_ROLES"));
  }

  @Test
  public void shouldThrowExceptionWhenPermissionIsMissing() {

    when(activeSessionService.getActiveToken()).thenReturn("token");

    when(jwtService.extractRoleId("token")).thenReturn(1);

    when(rolePermissionReadRepository.existsByRole_IdAndPermission_name(1, "GESTION_ROLES"))
        .thenReturn(false);

    assertThrows(
        IllegalStateException.class,
        () -> authorizationService.validatePermission("GESTION_ROLES"));
  }

  @Test
  public void shouldThrowExceptionWhenNoActiveSession() {

    when(activeSessionService.getActiveToken())
        .thenThrow(new SecurityException("No hay una sesión activa."));

    assertThrows(
        SecurityException.class, () -> authorizationService.validatePermission("GESTION_ROLES"));
  }
}
