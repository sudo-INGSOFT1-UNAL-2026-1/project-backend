package com.unerp.service.user;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.user.User;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.repository.user.UserWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserActivateService {

    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final AuthorizationService authorizationService;

    public UserActivateService(
            UserReadRepository userReadRepository,
            UserWriteRepository userWriteRepository,
            AuthorizationService authorizationService
    ) {
        this.userReadRepository = userReadRepository;
        this.userWriteRepository = userWriteRepository;
        this.authorizationService = authorizationService;
    }

    public User activateUser(Integer userId) {

        authorizationService.validatePermission(PermissionName.GESTION_ROLES);

        User user = userReadRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.activate();

        return userWriteRepository.save(user);
    }

}
