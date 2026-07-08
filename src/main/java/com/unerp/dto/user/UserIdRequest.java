package com.unerp.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserIdRequest(

    @NotNull
    Integer userId
){

}
