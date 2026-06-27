package com.unerp.service.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unerp.domain.role.Role;
import com.unerp.domain.user.User;
import com.unerp.domain.user.UserBuilder;
import com.unerp.domain.user.state.ActiveState;
import com.unerp.domain.user.state.InactiveState;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.service.auth.ActiveSessionService;
import com.unerp.service.auth.AuthorizationService;
import com.unerp.service.auth.JwtService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDeactivateServiceTest {

  @Mock private UserReadRepository userReadRepository;

  @Mock private UserWriteRepository userWriteRepository;

  @Mock private AuthorizationService authorizationService;

  @Mock private JwtService jwtService;

  @Mock private ActiveSessionService activeSessionService;

  @InjectMocks private UserDeactivateService userDeactivateService;

  @Test
  public void shouldDeactivateUserSuccessfully() {

    User user =
        new UserBuilder()
            .setId(2)
            .setName("Test User")
            .setEmail("test@gmail.com")
            .setState(new ActiveState())
            .setRole(new Role(1, "ADMIN_EMPRESA"))
            .build();

    when(userReadRepository.findById(user.getId())).thenReturn(Optional.of(user));

    when(activeSessionService.getActiveToken()).thenReturn("token");

    when(jwtService.extractUserId("token")).thenReturn(1);

    when(userWriteRepository.save(any(User.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    User result = userDeactivateService.deactivateUser(user.getId());

    assertNotNull(result);

    verify(userWriteRepository).save(user);
  }

  @Test
  public void shouldThrowExceptionWhenUserNotFound() {

    when(userReadRepository.findById(999)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> userDeactivateService.deactivateUser(999));
  }

  @Test
  public void shouldThrowExceptionWhenUserIsAlreadyInactive() {

    User user =
        new UserBuilder()
            .setId(2)
            .setName("Test User")
            .setEmail("test@gmail.com")
            .setState(new InactiveState())
            .setRole(new Role(1, "ADMIN_EMPRESA"))
            .build();

    when(userReadRepository.findById(user.getId())).thenReturn(Optional.of(user));

    when(activeSessionService.getActiveToken()).thenReturn("token");

    when(jwtService.extractUserId("token")).thenReturn(1);

    assertThrows(
        IllegalStateException.class, () -> userDeactivateService.deactivateUser(user.getId()));
  }

  @Test
  public void shouldThrowExceptionWhenTryingToDeactivateCurrentUser() {

    User user =
        new UserBuilder()
            .setId(1)
            .setName("Current User")
            .setEmail("current@gmail.com")
            .setState(new ActiveState())
            .setRole(new Role(1, "ADMIN_EMPRESA"))
            .build();

    when(userReadRepository.findById(user.getId())).thenReturn(Optional.of(user));

    when(activeSessionService.getActiveToken()).thenReturn("token");

    when(jwtService.extractUserId("token")).thenReturn(user.getId());

    assertThrows(
        IllegalArgumentException.class, () -> userDeactivateService.deactivateUser(user.getId()));
  }
}
