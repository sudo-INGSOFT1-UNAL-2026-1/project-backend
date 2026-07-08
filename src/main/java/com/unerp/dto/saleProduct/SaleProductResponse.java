package com.unerp.dto.saleProduct;

import java.math.BigDecimal;

import com.unerp.domain.sale.SaleProduct;

public record SaleProductResponse(
    Integer id,
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {
  public static SaleProductResponse fromDomain(SaleProduct sp) {
    return new SaleProductResponse(sp.getId(), sp.getProductId(), sp.getQuantity(), sp.getUnitPrice(), sp.getSubtotal());
  }
}

