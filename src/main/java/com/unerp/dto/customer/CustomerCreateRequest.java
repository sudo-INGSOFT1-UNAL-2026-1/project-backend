package com.unerp.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerCreateRequest(

    @NotBlank
    String name,

    String address
) {

}
