package com.unerp.service.purchase;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchase.PurchaseBuilder;
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchase.PurchaseWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.service.auth.AuthorizationService;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PurchaseCreateService {

  private final PurchaseReadRepository purchaseReadRepository;
  private final SupplierReadRepository supplierReadRepository;
  private final PurchaseWriteRepository purchaseWriteRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseCreateService(
      PurchaseReadRepository purchaseReadRepository,
      SupplierReadRepository supplierReadRepository,
      PurchaseWriteRepository purchaseWriteRepository,
      AuthorizationService authorizationService) {

    this.purchaseReadRepository = purchaseReadRepository;
    this.supplierReadRepository = supplierReadRepository;
    this.purchaseWriteRepository = purchaseWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Purchase createPurchase(
      String name,
      String description,
      Integer stock,
      BigDecimal price,
      String batch,
      LocalDate expirationDate,
      Integer supplierId) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    validateProductExists(name, batch);
    validateSupplierExists(supplierId);

    Product newProduct =
        new ProductBuilder()
            .setName(name)
            .setDescription(description)
            .setStock(stock)
            .setPrice(price)
            .setBatch(batch)
            .setExpirationDate(expirationDate)
            .setSupplierId(supplierId)
            .build();

    return productWriteRepository.save(newProduct);
  }

  private boolean validateNameExists(String name) {
    try {
      if (productReadRepository.existsByName(name)) {
        throw new IllegalArgumentException("Product with the same name already exists");
      }
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  private boolean validateBatchExists(String batch) {
    try {
      if (productReadRepository.existsByBatch(batch)) {
        throw new IllegalArgumentException("Product with the same batch already exists");
      }
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  private void validateProductExists(String name, String batch) {
    if (validateNameExists(name) && validateBatchExists(batch)) {
      throw new IllegalArgumentException("Product with the same name and batch already exists");
    }
  }

  private void validateSupplierExists(Integer id) {
    if (!supplierReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Supplier doesn't exists");
    }
  }
}
