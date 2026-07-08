package com.unerp.service.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchase.PurchaseBuilder;
import com.unerp.domain.purchase.state.PurchaseState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.repository.purchase.PurchaseReadRepository;
import com.unerp.repository.purchase.PurchaseSpecifications;
import com.unerp.repository.purchase.PurchaseWriteRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductReadRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductSpecifications;
import com.unerp.service.auth.AuthorizationService;

@Service
public class PurchaseGetService {

  private final PurchaseReadRepository purchaseReadRepository;
  private final PurchaseWriteRepository purchaseWriteRepository;
  private final PurchaseProductReadRepository purchaseProductReadRepository;
  private final AuthorizationService authorizationService;

  public PurchaseGetService(
      PurchaseReadRepository purchaseReadRepository,
      PurchaseWriteRepository purchaseWriteRepository,
      PurchaseProductReadRepository purchaseProductReadRepository,
      AuthorizationService authorizationService) {
    this.purchaseReadRepository = purchaseReadRepository;
    this.purchaseWriteRepository = purchaseWriteRepository;
    this.purchaseProductReadRepository = purchaseProductReadRepository;
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

    Purchase existingPurchase = getPurchaseById(id);

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
            .build();

    return purchaseWriteRepository.save(updatedPurchase);
  }

  public List<Purchase> getAllPurchases() {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseReadRepository.findAll();
  }

  public Purchase getPurchaseById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Purchase doesn't exist"));
  }

  public List<Purchase> getPurchasesByParameters(
      Integer supplierId,
      Integer userId,
      LocalDate paymentDate,
      LocalDate deliveryDate,
      String state,
      BigDecimal totalCost) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    PurchaseState purchaseState = state != null ? PurchaseState.fromName(state) : null;
    return purchaseReadRepository.findAll(
        PurchaseSpecifications.filterBy(
            supplierId, userId, paymentDate, deliveryDate, purchaseState, totalCost));
  }

  public List<PurchaseProduct> getAllPurchaseProducts() {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll();
  }

  public List<PurchaseProduct> getPurchaseProductsByParameters(
      Integer purchaseId,
      Integer productId,
      Integer quantity,
      BigDecimal unitPrice,
      BigDecimal subtotal) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll(
        PurchaseProductSpecifications.filterBy(
            purchaseId, productId, quantity, unitPrice, subtotal));
  }

  public List<PurchaseProduct> getAllPurchaseProductByPurchaseId(Integer purchaseId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll(
        PurchaseProductSpecifications.filterBy(purchaseId, null, null, null, null));
  }

  public List<PurchaseProduct> getAllPurchaseProductByProductId(Integer productId) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository.findAll(
        PurchaseProductSpecifications.filterBy(null, productId, null, null, null));
  }

  public PurchaseProduct getPurchaseProductById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    return purchaseProductReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("PurchaseProduct doesn't exist"));
  }
}