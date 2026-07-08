package com.unerp.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public record PurchaseSearchRequest (
    Integer supplierId,
    Integer userId,
    LocalDate paymentDate,
    LocalDate deliveryDate,
    String state,
    BigDecimal totalCost,
    List<PurchaseProduct> purchaseProducts
){
}