package com.unerp.service.user;

import com.unerp.domain.role.RoleName;
import com.unerp.security.PasswordHasher;
import com.unerp.domain.role.Role;
import com.unerp.domain.user.User;
import com.unerp.domain.user.UserBuilder;
import com.unerp.domain.user.state.ActiveState;
import com.unerp.repository.user.RoleReadRepository;
import com.unerp.repository.user.UserCreateRepository;
import com.unerp.repository.user.UserReadRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCreateService {

    private final UserReadRepository userReadRepository;
    private final UserCreateRepository userCreateRepository;
    private final RoleReadRepository roleReadRepository;
    private final PasswordHasher passwordHasher;

    public UserCreateService(
            UserReadRepository userReadRepository,
            UserCreateRepository userCreateRepository,
            RoleReadRepository roleReadRepository,
            PasswordHasher passwordHasher
    ) {
        this.userReadRepository = userReadRepository;
        this.userCreateRepository = userCreateRepository;
        this.passwordHasher = passwordHasher;
        this.roleReadRepository = roleReadRepository;
    }

    public User createUser(
            String name,
            String email,
            String password,
            String roleName
    ) {
        validateAvailableEmail(email);

        Role role = getRoleName(roleName);

        String passwordHash = passwordHasher.hash(password);

        User newUser = new UserBuilder()
                .setName(name)
                .setEmail(email)
                .setPasswordHash(passwordHash)
                .setState(new ActiveState())
                .setRoleId(role.getId())
                .build();

        return userCreateRepository.save(newUser);
    }

    private void validateAvailableEmail(String email) {
        if (userReadRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
    }

    private Role getRoleName(String roleName) {

        Role role;

        if (userReadRepository.count() != 0) {
            role = roleReadRepository.findByName(roleName);
            if (role == null) {
                throw new IllegalArgumentException("El rol especificado no existe");
        }
        return role;
    }
        role = roleReadRepository.findByName(RoleName.ADMIN_EMPRESA);
        return role;
    }
}