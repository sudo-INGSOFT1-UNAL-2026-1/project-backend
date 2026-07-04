package com.unerp.service.purchaseProduct;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductSpecifications;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseProductGetService {

  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final AuthorizationService authorizationService;

  public PurchaseProductGetService(
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.authorizationService = authorizationService;
  }

  public List<PurchaseProduct> getAllPurchaseProductByPurchaseId(Integer purchaseId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll();
  }

  public List<PurchaseProduct> getAllPurchaseProductByProductId(Integer productId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll();
  }

  public PurchaseProduct getPurchaseProductById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("PurchaseProduct doesn't exist"));
  }

  public List<PurchaseProduct> getPurchaseProductsByParameters(
      Integer purchaseId,
      Integer productId,
      Integer quantity,
      BigDecimal unitPrice,
      BigDecimal subtotal) {

    return purchaseProductReadRepository.findAll(
        PurchaseProductSpecifications.filterBy(
            purchaseId, productId, quantity, unitPrice, subtotal));
  }
}