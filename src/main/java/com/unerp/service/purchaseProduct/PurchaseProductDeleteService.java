package com.unerp.service.purchaseProduct;

import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseProductDeleteService {

  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseProductDeleteService(
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {

    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public void deleteProductById(Integer id) {
    if (!purchaseProductReadRepository.existsById(id)) {
      throw new IllegalArgumentException("PurchaseProduct doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    purchaseProductWriteRepository.deleteById(id);
  }

  public void deleteProductByPurchaseId(Integer purchaseId) {
    if (!purchaseProductReadRepository.existsByPurchaseId(purchaseId)) {
      throw new IllegalArgumentException("Purchase doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    purchaseProductWriteRepository.deleteByPurchaseId(purchaseId);
  }

  public void deleteProductByProductId(Integer productId) {
    if (!purchaseProductReadRepository.existsByProductId(productId)) {
      throw new IllegalArgumentException("Product doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    purchaseProductWriteRepository.deleteByProductId(productId);
  }
}
