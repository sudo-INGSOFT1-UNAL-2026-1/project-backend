package com.unerp.dto.sale;

public record SaleUpdateRequest(
    String deliveryDate,
    String description,
    String status
) {}

