package com.unerp.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.math.BigDecimal;
public record ProductCreateRequest(

    @NotBlank
    String name,

    @NotBlank
    String description,

    @NotNull
    @PositiveOrZero
    Integer stock,

    @NotNull
    @Positive
    BigDecimal price,

    @NotBlank
    String batch,

    @NotNull
    LocalDate expirationDate,

    @NotNull
    Integer supplierId
) {

}
