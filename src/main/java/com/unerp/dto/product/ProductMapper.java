package com.unerp.dto.product;

import com.unerp.domain.product.Product;

public final class ProductMapper {

  private ProductMapper() {}

  public static ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getStock(),
        product.getPrice(),
        product.getBatch(),
        product.getExpirationDate(),
        product.getSupplierId()
    );
  }
}
