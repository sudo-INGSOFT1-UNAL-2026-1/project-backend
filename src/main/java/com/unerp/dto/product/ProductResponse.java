package com.unerp.dto.product;

import java.time.LocalDate;
import java.math.BigDecimal;
public record ProductResponse(
    Integer id,
    String name,
    String description,
    Integer stock,
    BigDecimal price,
    String batch,
    LocalDate expirationDate,
    Integer supplierId
) {
}
