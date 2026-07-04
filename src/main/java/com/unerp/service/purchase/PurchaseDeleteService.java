package com.unerp.service.product;

import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseDeleteService {

  private final ProductReadRepository productReadRepository;
  private final SupplierReadRepository supplierReadRepository;
  private final ProductWriteRepository productWriteRepository;
  private final AuthorizationService authorizationService;

  public ProductDeleteService(
      ProductReadRepository productReadRepository,
      SupplierReadRepository supplierReadRepository,
      ProductWriteRepository productWriteRepository,
      AuthorizationService authorizationService) {

    this.productReadRepository = productReadRepository;
    this.supplierReadRepository = supplierReadRepository;
    this.productWriteRepository = productWriteRepository;
    this.authorizationService = authorizationService;
  }

  public void deleteProductById(Integer id) {
    if (!productReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Product doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    productWriteRepository.deleteById(id);
  }

  public void deleteProductByBatch(String batch) {
    if (!productReadRepository.existsByBatch(batch)) {
      throw new IllegalArgumentException("Batch doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    productWriteRepository.deleteByBatch(batch);
  }

  public void deleteProductBySupplierId(Integer supplierId) {
    if (!supplierReadRepository.existsById(supplierId)) {
      throw new IllegalArgumentException("Supplier doesn't exist");
    }
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    productWriteRepository.deleteBySupplierId(supplierId);
  }
}
