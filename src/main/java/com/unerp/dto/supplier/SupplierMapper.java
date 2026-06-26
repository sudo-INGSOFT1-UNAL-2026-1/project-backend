package com.unerp.dto.supplier;

import com.unerp.domain.supplier.Supplier;

public final class SupplierMapper {

  private SupplierMapper() {}

  public static SupplierResponse toResponse(Supplier supplier) {
    return new SupplierResponse(
        supplier.getId(),
        supplier.getName(),
        supplier.getPhone(),
        supplier.getEmail()
    );
  }
}
