package com.unerp.dto.purchase;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PurchaseCreateRequest(

    @NotNull
    @Positive
    Integer supplierId,

    @NotNull
    @Positive
    Integer userId,

    @NotNull
    LocalDate paymentDate,

    @NotNull
    LocalDate deliveryDate,

    @NotNull
    @Positive
    BigDecimal totalCost,

    @NotEmpty
    List<@Valid PurchaseProductRequest> products

) {
}