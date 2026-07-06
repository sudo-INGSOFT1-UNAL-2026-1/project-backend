package com.unerp.dto.purchase;

import com.unerp.domain.purchase.Purchase;

public final class PurchaseMapper {

  private PurchaseMapper() {}

  public static PurchaseResponse toResponse(Purchase purchase) {
    return new PurchaseResponse(
        purchase.getId(),
        purchase.getSupplierId(),
        purchase.getUserId(),
        purchase.getPaymentDate(),
        purchase.getDeliveryDate(),
        purchase.getState(),
        purchase.getTotalCost(),
        purchase.getPurchaseProducts()
    );
  }
}
