package com.unerp.service.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchase.PurchaseBuilder;
import com.unerp.domain.purchase.state.PurchaseState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.domain.purchaseProduct.PurchaseProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchase.PurchaseWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.user.UserReadRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseCreateService {
  private final PurchaseWriteRepository purchaseWriteRepository;
  private final PurchaseReadRepository purchaseReadRepository;
  private final ProductReadRepository productReadRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final SupplierReadRepository supplierReadRepository;
  private final UserReadRepository userReadRepository;
  private final AuthorizationService authorizationService;

  public PurchaseCreateService(
      PurchaseWriteRepository purchaseWriteRepository,
      ProductReadRepository productReadRepository,
      SupplierReadRepository supplierReadRepository,
      UserReadRepository userReadRepository,
      PurchaseReadRepository purchaseReadRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {

    this.purchaseWriteRepository = purchaseWriteRepository;
    this.productReadRepository = productReadRepository;
    this.supplierReadRepository = supplierReadRepository;
    this.userReadRepository = userReadRepository;
    this.purchaseReadRepository = purchaseReadRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Purchase createPurchase(
      Integer supplierId,
      Integer userId,
      LocalDate paymentDate,
      LocalDate deliveryDate,
      PurchaseState state,
      BigDecimal totalCost) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    
    validateSupplierExists(supplierId);
    validateUserExists(userId);

    Purchase newProduct =
        new PurchaseBuilder()
            .setSupplierId(supplierId)
            .setUserId(userId)
            .setPaymentDate(paymentDate)
            .setDeliveryDate(deliveryDate)
            .setState(state)
            .setTotalCost(totalCost)
            .build();

    return purchaseWriteRepository.save(newProduct);
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

  private void validateSupplierExists(Integer id) {
    if (!supplierReadRepository.existsById(id)) {
      throw new IllegalArgumentException("Supplier doesn't exists");
    }
  }

  private void validateUserExists(Integer id) {
    if (!userReadRepository.existsById(id)) {
      throw new IllegalArgumentException("User doesn't exists");
    }
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
