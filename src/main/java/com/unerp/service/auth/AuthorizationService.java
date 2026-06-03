package com.unerp.service.auth;

import com.unerp.repository.user.RolePermissionReadRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final RolePermissionReadRepository rolePermissionReadRepository;
    private final JwtService jwtService;

    public AuthorizationService(RolePermissionReadRepository rolePermissionReadRepository, JwtService jwtService) {
        this.rolePermissionReadRepository = rolePermissionReadRepository;
        this.jwtService = jwtService;
    }

    public void validatePermission(String token, String permissionName) {

        Integer roleId = jwtService.extractRoleId(token);

        boolean hasPermission = rolePermissionReadRepository.existsByRole_IdAndPermission_name(roleId, permissionName);

        if (!hasPermission) {
            throw new RuntimeException("No tienes permiso para realizar esta acción.");
        }
    }
}
