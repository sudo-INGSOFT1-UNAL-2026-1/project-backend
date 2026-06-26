package com.unerp.dto.product;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ProductCreateRequest(

    @NotBlank
    String name,

    @NotBlank
    String description,

    @NotBlank
    Integer stock,

    @NotBlank
    Double price,

    @NotBlank
    String batch,

    @NotBlank
    LocalDate expirationDate,

    @NotBlank
    Integer supplierId
) {

}
