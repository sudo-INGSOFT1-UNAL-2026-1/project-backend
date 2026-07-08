package com.unerp.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchase.state.PurchaseState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;

public record PurchaseSearchRequest (
    Integer supplierId,
    Integer userId,
    LocalDate paymentDate,
    LocalDate deliveryDate,
    PurchaseState state,
    BigDecimal totalCost,
    List<PurchaseProduct> purchaseProducts
){
}