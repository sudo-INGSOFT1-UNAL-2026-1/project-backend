package com.unerp.service.purchaseProduct;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.product.Product;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.domain.purchaseProduct.PurchaseProductBuilder;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.product.ProductSpecifications;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PurchaseProductGetService {

  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseProductGetService(
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

    PurchaseProduct existingPurchaseProduct = getPurchaseProductById(id);

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

  public List<PurchaseProduct> getAllPurchaseProductByPurchaseId(Integer purchaseId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll();
  }

  public PurchaseProduct getPurchaseProductById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("PurchaseProduct doesn't exist"));
  }

  public List<Product> getProductsByParameters(
      String name,
      String description,
      Integer stock,
      BigDecimal price,
      String batch,
      LocalDate expirationDate,
      Integer supplierId) {

    return purchaseProductReadRepository.findAll(
        ProductSpecifications.filterBy(
            name, description, stock, price, batch, expirationDate, supplierId));
  }
}