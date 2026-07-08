package com.unerp.dto.purchaseProduct;

import java.math.BigDecimal;

public record PurchaseProductSearchRequest (
    Integer id,
    Integer purchaseId,
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
){
}
