package com.unerp.dto.product;

import java.time.LocalDate;

public record ProductResponse(
    Integer id,
    String name,
    String description,
    Integer stock,
    Double price,
    String batch,
    LocalDate expirationDate,
    Integer supplierId
) {
}
