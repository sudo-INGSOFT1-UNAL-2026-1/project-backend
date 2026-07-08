package com.unerp.dto.saleProduct;

import java.math.BigDecimal;

public record SaleProductRequest(
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice
) {}

