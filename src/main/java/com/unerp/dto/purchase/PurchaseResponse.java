package com.unerp.dto.purchase;

import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

import java.math.BigDecimal;

public record PurchaseResponse(
    Integer id,
    Integer supplierId,
    Integer userId,
    LocalDate paymentDate,
    LocalDate deliveryDate,
    String state,
    BigDecimal totalCost,
    List<PurchaseProduct> purchaseProducts
) {
}
