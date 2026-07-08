package com.unerp.dto.sale;

import java.util.List;
import com.unerp.dto.saleProduct.SaleProductRequest;

public record SaleCreateRequest(
    Integer customerId,
    Integer userId,
    String customerName,
    String customerAddress,
    String description,
    String deliveryDate,
    List<SaleProductRequest> products
) {}

