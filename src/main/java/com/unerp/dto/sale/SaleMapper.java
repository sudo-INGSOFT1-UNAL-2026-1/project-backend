package com.unerp.dto.sale;

import com.unerp.domain.sale.Sale;

public final class SaleMapper {
  private SaleMapper() {}
  public static SaleResponse toResponse(Sale s) { return SaleResponse.fromDomain(s); }
}

