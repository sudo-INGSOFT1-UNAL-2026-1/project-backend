package com.unerp.dto.purchaseProduct;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record PurchaseProductCreateRequest(
    
    @NotBlank
    Integer id,
    
    @NotBlank
    Integer purchaseId,

    @NotBlank
    Integer productId,

    @NotBlank
    Integer quantity,

    @NotBlank
    BigDecimal unitPrice,

    @NotBlank
    BigDecimal subtotal
) {

}
