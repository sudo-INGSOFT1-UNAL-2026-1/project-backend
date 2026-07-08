package com.unerp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(

    @NotNull
    Integer userId,

    @NotBlank
    String newRoleName
) { }
