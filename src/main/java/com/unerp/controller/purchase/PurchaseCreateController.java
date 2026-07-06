package com.unerp.controller.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchase.state.PendingState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.dto.purchase.PurchaseCreateRequest;
import com.unerp.dto.purchase.PurchaseMapper;
import com.unerp.service.purchase.PurchaseCreateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/purchase")
public class PurchaseCreateController {

  private final PurchaseCreateService purchaseCreateService;

  public PurchaseCreateController(PurchaseCreateService purchaseCreateService) {
    this.purchaseCreateService = purchaseCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createPurchase(@Valid @RequestBody PurchaseCreateRequest request) {
    try {
      BigDecimal totalCost = BigDecimal.ZERO;
      for (var product : request.products()) {
        totalCost = totalCost.add(product.unitPrice().multiply(BigDecimal.valueOf(product.quantity())));
      }

      Purchase purchase =
          purchaseCreateService.createPurchase(
              request.supplierId(),
              request.userId(),
              request.paymentDate(),
              request.deliveryDate(),
              new PendingState(),
              totalCost);

      List<PurchaseProduct> createdProducts = new ArrayList<>();
      for (var product : request.products()) {
        createdProducts.add(
            purchaseCreateService.createPurchaseProduct(
                purchase.getId(),
                product.productId(),
                product.quantity(),
                product.unitPrice()));
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(PurchaseMapper.toResponse(purchase));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
