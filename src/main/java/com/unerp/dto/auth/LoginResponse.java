package com.unerp.dto.auth;

public record LoginResponse (

    String token,
    Integer id,
    String name,
    String email,
    String state,
    String role
){

}
