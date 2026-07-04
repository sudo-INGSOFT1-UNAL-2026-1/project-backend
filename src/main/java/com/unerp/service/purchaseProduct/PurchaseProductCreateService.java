package com.unerp.service.purchaseProduct;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.domain.purchaseProduct.PurchaseProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseProductCreateService {

  private final PurchaseReadRepository purchaseReadRepository;
  private final ProductReadRepository productReadRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseProductCreateService(
      PurchaseReadRepository purchaseReadRepository,
      ProductReadRepository productReadRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {

    this.purchaseReadRepository = purchaseReadRepository;
    this.productReadRepository = productReadRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public PurchaseProduct createPurchaseProduct(
      Integer purchaseId,
      Integer productId,
      Integer quantity,
      BigDecimal unitPrice) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    validateProductExists(productId);
    validatePurchaseExists(purchaseId);
    validateUniquePurchaseProduct(purchaseId, productId);

    BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

    PurchaseProduct newPurchaseProduct =
        new PurchaseProductBuilder()
            .setPurchaseId(purchaseId)
            .setProductId(productId)
            .setQuantity(quantity)
            .setUnitPrice(unitPrice)
            .setSubtotal(subtotal)
            .build();

    return purchaseProductWriteRepository.save(newPurchaseProduct);
  }

  private void validateProductExists(Integer id) {
    if (!productReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Product doesn't exists");
    }
  }

  private void validatePurchaseExists(Integer id) {
    if (!purchaseReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Purchase doesn't exists");
    }
  }

  private void validateUniquePurchaseProduct(Integer purchaseId, Integer productId) {
    if (purchaseProductReadRepository.existsByPurchaseIdAndProductId(purchaseId, productId)) {
      throw new IllegalArgumentException("Product already exists in Purchase");
    }
  }
}
