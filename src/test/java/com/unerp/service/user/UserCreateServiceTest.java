package com.unerp.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.unerp.domain.role.Role;
import com.unerp.domain.role.RoleName;
import com.unerp.domain.user.User;
import com.unerp.repository.user.*;
import com.unerp.security.PasswordHasher;
import com.unerp.service.auth.AuthorizationService;
import com.unerp.service.auth.FirstUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserCreateServiceTest {

  @Mock private UserReadRepository userReadRepository;

  @Mock private UserWriteRepository userWriteRepository;

  @Mock private RoleReadRepository roleReadRepository;

  @Mock private PasswordHasher passwordHasher;

  @Mock private AuthorizationService authorizationService;

  @Mock private FirstUserService firstUserService;

  @InjectMocks private UserCreateService userCreateService;

  @Test
  public void shouldThrowExceptionWhenEmailAlreadyExists() {

    when(userReadRepository.existsByEmail("angel@gmail.com")).thenReturn(true);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            userCreateService.createUser(
                "Angel", "angel@gmail.com", "password123", RoleName.ADMIN_EMPRESA));
  }

  @Test
  public void shouldAssignAdminRoleToFirstUser() {

    Role adminRole = new Role(1, RoleName.ADMIN_EMPRESA);

    when(firstUserService.isFirstUser()).thenReturn(true);
    when(userReadRepository.existsByEmail("angel@gmail")).thenReturn(false);
    when(roleReadRepository.findByName(RoleName.ADMIN_EMPRESA)).thenReturn(adminRole);
    when(passwordHasher.hash("password123")).thenReturn("hashedPassword");
    when(userWriteRepository.save(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    User createdUser =
        userCreateService.createUser("Angel", "angel@gmail", "password123", "GERENTE");

    assertEquals(RoleName.ADMIN_EMPRESA, createdUser.getRole().getName());
  }
}
