package com.unerp.service.purchaseProduct;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.domain.purchaseProduct.PurchaseProductBuilder;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseProductUpdateService {

  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseProductUpdateService(
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public PurchaseProduct updatePurchaseProduct(
      Integer id,
      Integer purchaseId,
      Integer productId,
      Integer quantity,
      BigDecimal unitPrice,
      BigDecimal subtotal) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);

    PurchaseProduct existingPurchaseProduct = purchaseProductReadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("PurchaseProduct doesn't exist"));

    Integer existingPurchaseId = existingPurchaseProduct.getPurchaseId();
    Integer existingProductId = existingPurchaseProduct.getProductId();
    Integer existingQuantity = existingPurchaseProduct.getQuantity();
    BigDecimal existingUnitPrice = existingPurchaseProduct.getUnitPrice();
    BigDecimal existingSubtotal = existingPurchaseProduct.getSubtotal();

    PurchaseProduct updatedPurchaseProduct =
        new PurchaseProductBuilder()
            .setId(id)
            .setPurchaseId(purchaseId != null ? purchaseId : existingPurchaseId)
            .setProductId(productId != null ? productId : existingProductId)
            .setQuantity(quantity != null ? quantity : existingQuantity)
            .setUnitPrice(unitPrice != null ? unitPrice : existingUnitPrice)
            .setSubtotal(subtotal != null ? subtotal : existingSubtotal)
            .build();

    return purchaseProductWriteRepository.save(updatedPurchaseProduct);
  }
}