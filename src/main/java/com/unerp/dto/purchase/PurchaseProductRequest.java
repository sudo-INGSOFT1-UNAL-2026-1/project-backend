package com.unerp.dto.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record PurchaseProductRequest(

    @NotNull
    Integer productId,

    @NotNull
    @PositiveOrZero
    Integer quantity,
    
    @NotNull
    @PositiveOrZero
    BigDecimal unitPrice) {
}
