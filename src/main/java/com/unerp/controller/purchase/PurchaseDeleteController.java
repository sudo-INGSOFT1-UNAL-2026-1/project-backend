package com.unerp.controller.purchase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.service.purchase.PurchaseDeleteService;

@RestController
@RequestMapping("/purchase")
public class PurchaseDeleteController {

  private final PurchaseDeleteService purchaseDeleteService;

  public PurchaseDeleteController(PurchaseDeleteService purchaseDeleteService) {
    this.purchaseDeleteService = purchaseDeleteService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePurchaseById(@PathVariable Integer id) {
    try {
      purchaseDeleteService.deletePurchaseById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Purchase deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/supplier/{supplierId}")
  public ResponseEntity<?> deletePurchaseBySupplierId(@PathVariable Integer supplierId) {
    try {
      purchaseDeleteService.deletePurchaseBySupplierId(supplierId);
      return ResponseEntity.status(HttpStatus.OK).body("Purchase deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/product/{id}")
  public ResponseEntity<?> deletePurchaseProductById(@PathVariable Integer id) {
    try {
      purchaseDeleteService.deletePurchaseProductById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Purchase product deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/product/{purchaseId}")
  public ResponseEntity<?> deletePurchaseProductByPurchaseId(@PathVariable Integer purchaseId) {
    try {
      purchaseDeleteService.deletePurchaseProductByPurchaseId(purchaseId);
      return ResponseEntity.status(HttpStatus.OK).body("Purchase of products deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @DeleteMapping("/product/{productId}")
  public ResponseEntity<?> deletePurchaseProductByProductId(@PathVariable Integer id) {
    try {
      purchaseDeleteService.deletePurchaseProductByProductId(id);
      return ResponseEntity.status(HttpStatus.OK).body("Purchase of product deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
