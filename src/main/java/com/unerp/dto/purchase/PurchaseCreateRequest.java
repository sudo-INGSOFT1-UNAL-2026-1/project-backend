package com.unerp.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchase.state.PurchaseState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PurchaseCreateRequest(

    @NotBlank
    Integer supplierId,

    @NotBlank
    Integer userId,

    @NotBlank
    LocalDate paymentDate,

    @NotBlank
    LocalDate deliveryDate,

    @NotBlank
    PurchaseState state,

    @NotBlank
    BigDecimal totalCost,

    @NotBlank
    List<@Valid PurchaseProductRequest> products) {
}
