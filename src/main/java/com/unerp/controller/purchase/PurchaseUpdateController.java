package com.unerp.controller.purchase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.dto.purchase.PurchaseMapper;
import com.unerp.dto.purchase.PurchaseSearchRequest;
import com.unerp.dto.purchaseProduct.PurchaseProductMapper;
import com.unerp.dto.purchaseProduct.PurchaseProductSearchRequest;
import com.unerp.service.purchase.PurchaseUpdateService;

@RestController
@RequestMapping("/purchase")
public class PurchaseUpdateController {

  private final PurchaseUpdateService purchaseUpdateService;

  public PurchaseUpdateController(PurchaseUpdateService purchaseUpdateService) {
    this.purchaseUpdateService = purchaseUpdateService;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updatePurchase(
      @PathVariable Integer id,
      @RequestBody PurchaseSearchRequest request
  ) {
    try {

      Purchase purchase =
          purchaseUpdateService.updatePurchase(
              id,
              request.supplierId(),
              request.userId(),
              request.paymentDate(),
              request.deliveryDate(),
              request.state(),
              request.totalCost()
          );

      return ResponseEntity.status(HttpStatus.OK).body(PurchaseMapper.toResponse(purchase));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @PutMapping("/product/update/{id}")
  public ResponseEntity<?> updatePurchaseProduct(
      @PathVariable Integer id,
      @RequestBody PurchaseProductSearchRequest request
  ) {
    try {

      PurchaseProduct purchaseProduct =
          purchaseUpdateService.updatePurchaseProduct(
              id,
              request.purchaseId(),
              request.productId(),
              request.quantity(),
              request.unitPrice(),
              request.subtotal()
          );

      return ResponseEntity.status(HttpStatus.OK).body(PurchaseProductMapper.toResponse(purchaseProduct));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
