package com.unerp.dto.product;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.math.BigDecimal;
public record ProductCreateRequest(

    @NotBlank
    String name,

    @NotBlank
    String description,

    @NotBlank
    Integer stock,

    @NotBlank
    BigDecimal price,

    @NotBlank
    String batch,

    @NotBlank
    LocalDate expirationDate,

    @NotBlank
    Integer supplierId
) {

}
