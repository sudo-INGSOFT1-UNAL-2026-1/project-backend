package com.unerp.dto.purchase;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record PurchaseProductRequest(

    @NotBlank
    Integer productId,

    @NotBlank
    Integer quantity,
    
    @NotBlank
    BigDecimal unitPrice) {
}
