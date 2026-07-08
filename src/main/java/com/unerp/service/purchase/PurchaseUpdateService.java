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
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchase.PurchaseWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseUpdateService {

  private final PurchaseReadRepository purchaseReadRepository;
  private final PurchaseWriteRepository purchaseWriteRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final PurchaseProductWriteRepository purchaseProductWriteRepository;
  private final AuthorizationService authorizationService;

  public PurchaseUpdateService(
      PurchaseReadRepository purchaseReadRepository,
      PurchaseWriteRepository purchaseWriteRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      PurchaseProductWriteRepository purchaseProductWriteRepository,
      AuthorizationService authorizationService) {
    this.purchaseReadRepository = purchaseReadRepository;
    this.purchaseWriteRepository = purchaseWriteRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
    this.purchaseProductWriteRepository = purchaseProductWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Purchase updatePurchase(
      Integer id,
      Integer supplierId,
      Integer userId,
      LocalDate paymentDate,
      LocalDate deliveryDate,
      String state,
      BigDecimal totalCost) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    Purchase existingPurchase =
        purchaseReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Purchase doesn't exist"));

    Integer existingSupplierId = existingPurchase.getSupplierId();
    Integer existingUserId = existingPurchase.getUserId();
    LocalDate existingPaymentDate = existingPurchase.getPaymentDate();
    LocalDate existingDeliveryDate = existingPurchase.getDeliveryDate();
    PurchaseState existingState = existingPurchase.getState();
    BigDecimal existingTotalCost = existingPurchase.getTotalCost();

    PurchaseState newState = state != null ? PurchaseState.fromName(state) : existingState;

    Purchase updatedPurchase =
        new PurchaseBuilder()
            .setId(id)
            .setSupplierId(supplierId != null ? supplierId : existingSupplierId)
            .setUserId(userId != null ? userId : existingUserId)
            .setPaymentDate(paymentDate != null ? paymentDate : existingPaymentDate)
            .setDeliveryDate(deliveryDate != null ? deliveryDate : existingDeliveryDate)
            .setState(newState)
            .setTotalCost(totalCost != null ? totalCost : existingTotalCost)
            .setPurchaseProducts(existingPurchase.getPurchaseProducts())
            .build();

    return purchaseWriteRepository.save(updatedPurchase);
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