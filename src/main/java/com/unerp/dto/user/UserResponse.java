package com.unerp.dto.user;

public record UserResponse (

    Integer id,
    String name,
    String email,
    String state,
    String roleName

){
}
