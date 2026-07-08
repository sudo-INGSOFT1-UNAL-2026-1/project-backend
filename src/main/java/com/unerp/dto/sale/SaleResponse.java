package com.unerp.dto.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.sale.Sale;
import com.unerp.dto.saleProduct.SaleProductResponse;

public record SaleResponse(
    Integer id,
    Integer customerId,
    String description,
    LocalDate deliveryDate,
    String status,
    BigDecimal totalCost,
    List<SaleProductResponse> products
) {
  public static SaleResponse fromDomain(Sale s) {
    List<SaleProductResponse> products = s.getProducts() == null ? List.of() : s.getProducts().stream().map(SaleProductResponse::fromDomain).toList();
    return new SaleResponse(s.getId(), s.getCustomerId(), s.getDescription(), s.getDeliveryDate(), s.getStatus().name(), s.getTotalCost(), products);
  }
}

