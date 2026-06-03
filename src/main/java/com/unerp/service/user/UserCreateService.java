package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.role.RoleName;
import com.unerp.security.PasswordHasher;
import com.unerp.domain.role.Role;
import com.unerp.domain.user.User;
import com.unerp.domain.user.UserBuilder;
import com.unerp.domain.user.state.ActiveState;
import com.unerp.repository.user.RoleReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.service.auth.ActiveSessionService;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class UserCreateService {

    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final RoleReadRepository roleReadRepository;
    private final PasswordHasher passwordHasher;
    private final AuthorizationService authorizationService;
    private final ActiveSessionService activeSessionService;

    public UserCreateService(
            UserReadRepository userReadRepository,
            UserWriteRepository userWriteRepository,
            RoleReadRepository roleReadRepository,
            PasswordHasher passwordHasher,
            AuthorizationService authorizationService,
            ActiveSessionService activeSessionService
    ) {
        this.userReadRepository = userReadRepository;
        this.userWriteRepository = userWriteRepository;
        this.passwordHasher = passwordHasher;
        this.roleReadRepository = roleReadRepository;
        this.authorizationService = authorizationService;
        this.activeSessionService = activeSessionService;

    }

    public User createUser(
            String name,
            String email,
            String password,
            String roleName
    ) {
        validateAvailableEmail(email);

        Role role;
        String token = activeSessionService.getActiveToken();

        if (notIsFirstUser()) {
            authorizationService.validatePermission(token, PermissionName.GESTION_ROLES);
            role = getRole(roleName);
        } else {
            role = getRole(RoleName.ADMIN_EMPRESA);
        }

        String passwordHash = passwordHasher.hash(password);

        User newUser = new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setPasswordHash(passwordHash)
                .setState(new ActiveState())
                .setRole(role)
                .build();

        return userWriteRepository.save(newUser);
    }

    private void validateAvailableEmail(String email) {
        if (userReadRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
    }

    private Role getRole(String roleName) {

        Role role = roleReadRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("El rol especificado no existe");
        }
        return role;
    }

    private boolean notIsFirstUser() {
        return userReadRepository.count() != 0;
    }
}