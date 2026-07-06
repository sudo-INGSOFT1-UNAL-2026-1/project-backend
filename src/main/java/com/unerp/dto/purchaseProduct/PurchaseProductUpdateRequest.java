package com.unerp.dto.purchaseProduct;

import java.math.BigDecimal;

public record PurchaseProductUpdateRequest(
    Integer id,
    Integer purchaseId,
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {

}