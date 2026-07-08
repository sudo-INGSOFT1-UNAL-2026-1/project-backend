package com.unerp.dto.purchaseProduct;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public final class PurchaseProductMapper {

  private PurchaseProductMapper() {}

  public static PurchaseProductResponse toResponse(PurchaseProduct purchaseProduct) {
    return new PurchaseProductResponse(
        purchaseProduct.getId(),
        purchaseProduct.getPurchaseId(),
        purchaseProduct.getProductId(),
        purchaseProduct.getQuantity(),
        purchaseProduct.getUnitPrice(),
        purchaseProduct.getSubtotal()
    );
  }
}