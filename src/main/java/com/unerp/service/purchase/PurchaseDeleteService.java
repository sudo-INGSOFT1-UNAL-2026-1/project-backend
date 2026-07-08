package com.unerp.service.purchase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchase.PurchaseWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
@Transactional
public class PurchaseDeleteService {

  private final PurchaseReadRepository purchaseReadRepository;
  private final PurchaseWriteRepository purchaseWriteRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final ProductReadRepository productReadRepository;
  private final SupplierReadRepository supplierReadRepository;
  private final AuthorizationService authorizationService;

  public PurchaseDeleteService(
      PurchaseReadRepository purchaseReadRepository,
      PurchaseWriteRepository purchaseWriteRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      ProductReadRepository productReadRepository,
      SupplierReadRepository supplierReadRepository,
      AuthorizationService authorizationService) {

    this.purchaseReadRepository = purchaseReadRepository;
    this.purchaseWriteRepository = purchaseWriteRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.productReadRepository = productReadRepository;
    this.supplierReadRepository = supplierReadRepository;
    this.authorizationService = authorizationService;
  }

  public void deletePurchaseById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    if (!purchaseReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Purchase doesn't exist");
    }
    purchaseWriteRepository.deleteById(id);
  }

  public void deletePurchaseBySupplierId(Integer supplierId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    if (!supplierReadRepository.existsById(supplierId)) {
      throw new IllegalArgumentException("Supplier doesn't exist");
    }
    purchaseWriteRepository.deleteBySupplierId(supplierId);
  }

  public void deletePurchaseProductById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    if (!productReadRepository.existsById(id)) {
      throw new IllegalArgumentException("PurchaseProduct doesn't exist");
    }
    purchaseProductWriteRepository.deleteById(id);
  }

  public void deletePurchaseProductByPurchaseId(Integer purchaseId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    if (!purchaseProductReadRepository.existsByPurchaseId(purchaseId)) {
      throw new IllegalArgumentException("Purchase doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    purchaseProductWriteRepository.deleteByPurchaseId(purchaseId);
  }

  public void deletePurchaseProductByProductId(Integer productId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    if (!purchaseProductReadRepository.existsByProductId(productId)) {
      throw new IllegalArgumentException("Product doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    purchaseProductWriteRepository.deleteByProductId(productId);
  }
}
