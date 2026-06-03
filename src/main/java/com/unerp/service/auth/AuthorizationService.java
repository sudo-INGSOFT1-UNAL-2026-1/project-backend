package com.unerp.service.auth;

import com.unerp.repository.user.RolePermissionReadRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final RolePermissionReadRepository rolePermissionReadRepository;
    private final JwtService jwtService;
    private final ActiveSessionService activeSessionService;

    public AuthorizationService(
            RolePermissionReadRepository rolePermissionReadRepository,
            JwtService jwtService,
            ActiveSessionService activeSessionService
    ) {
        this.rolePermissionReadRepository = rolePermissionReadRepository;
        this.jwtService = jwtService;
        this.activeSessionService = activeSessionService;
    }

    public void validatePermission(String permissionName) {

        String token = activeSessionService.getActiveToken();
        Integer roleId = jwtService.extractRoleId(token);


        boolean hasPermission = rolePermissionReadRepository.existsByRole_IdAndPermission_name(roleId, permissionName);

        if (!hasPermission) {
            throw new RuntimeException("No tienes permiso para realizar esta acción.");
        }
    }
}
