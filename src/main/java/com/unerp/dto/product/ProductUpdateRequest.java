package com.unerp.dto.product;

import java.time.LocalDate;

public record ProductUpdateRequest(

    String name,
    String description,
    Integer stock,
    Double price,
    String batch,
    LocalDate expirationDate,
    Integer supplierId
) {

}
