package com.unerp.service.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.domain.purchaseProduct.PurchaseProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseUpdateService {

  private final ProductReadRepository productReadRepository;
  private final ProductWriteRepository productWriteRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseUpdateService(
      ProductReadRepository productReadRepository,
      ProductWriteRepository productWriteRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {
    this.productReadRepository = productReadRepository;
    this.productWriteRepository = productWriteRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Product updateProduct(
      Integer id,
      String name,
      String description,
      Integer stock,
      BigDecimal price,
      String batch,
      LocalDate expirationDate,
      Integer supplierId) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    Product existingProduct =
        productReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product doesn't exist"));

    String existingName = existingProduct.getName();
    String existingDescription = existingProduct.getDescription();
    Integer existingStock = existingProduct.getStock();
    BigDecimal existingPrice = existingProduct.getPrice();
    String existingBatch = existingProduct.getBatch();
    LocalDate existingExpirationDate = existingProduct.getExpirationDate();
    Integer existingSupplierId = existingProduct.getSupplierId();

    Product updatedProduct =
        new ProductBuilder()
            .setId(id)
            .setName(name != null ? name : existingName)
            .setDescription(description != null ? description : existingDescription)
            .setStock(stock != null ? stock : existingStock)
            .setPrice(price != null ? price : existingPrice)
            .setBatch(batch != null ? batch : existingBatch)
            .setExpirationDate(expirationDate != null ? expirationDate : existingExpirationDate)
            .setSupplierId(supplierId != null ? supplierId : existingSupplierId)
            .build();

    return productWriteRepository.save(updatedProduct);
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