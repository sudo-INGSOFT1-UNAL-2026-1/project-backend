package com.unerp.controller.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.dto.purchase.PurchaseMapper;
import com.unerp.dto.purchase.PurchaseResponse;
import com.unerp.dto.purchase.PurchaseSearchRequest;
import com.unerp.dto.purchaseProduct.PurchaseProductMapper;
import com.unerp.dto.purchaseProduct.PurchaseProductResponse;
import com.unerp.dto.purchaseProduct.PurchaseProductSearchRequest;
import com.unerp.service.purchase.PurchaseGetService;

@RestController
@RequestMapping("/purchase")
public class PurchaseGetController {

  private final PurchaseGetService purchaseGetService;

  public PurchaseGetController(PurchaseGetService purchaseGetService) {
    this.purchaseGetService = purchaseGetService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllPurchases() {
    try {

      List<Purchase> purchases = purchaseGetService.getAllPurchases();

      List<PurchaseResponse> responseBody = new ArrayList<>();

      for (Purchase purchase : purchases) {
        responseBody.add(PurchaseMapper.toResponse(purchase));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPurchaseById(@PathVariable Integer id) {
    try {

      Purchase purchase = purchaseGetService.getPurchaseById(id);

      return ResponseEntity.status(HttpStatus.OK).body(PurchaseMapper.toResponse(purchase));

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/search")
  public ResponseEntity<?> getPurchasesByParameters(
      PurchaseSearchRequest request) {
    try {

      List<Purchase> purchases =
          purchaseGetService.getPurchasesByParameters(
              request.supplierId(),
              request.userId(),
              request.paymentDate(),
              request.deliveryDate(),
              request.state(),
              request.totalCost()
          );

      List<PurchaseResponse> responseBody = new ArrayList<>();
      for (Purchase purchase : purchases) {
        responseBody.add(PurchaseMapper.toResponse(purchase));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/products/all")
  public ResponseEntity<?> getAllPurchaseProducts() {
    try {

      List<PurchaseProduct> purchaseProducts = purchaseGetService.getAllPurchaseProducts();

      List<PurchaseProductResponse> responseBody = new ArrayList<>();

      for (PurchaseProduct purchaseProduct : purchaseProducts) {
        responseBody.add(PurchaseProductMapper.toResponse(purchaseProduct));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<?> getPurchaseProductById(@PathVariable Integer id) {
    try {

      PurchaseProduct purchaseProduct = purchaseGetService.getPurchaseProductById(id);

      return ResponseEntity.status(HttpStatus.OK).body(PurchaseProductMapper.toResponse(purchaseProduct));

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/search/products")
  public ResponseEntity<?> getPurchaseProductsByParameters(
      PurchaseProductSearchRequest request) {
    try {

      List<PurchaseProduct> purchaseProducts =
          purchaseGetService.getPurchaseProductsByParameters(
              request.purchaseId(),
              request.productId(),
              request.quantity(),
              request.unitPrice(),
              request.subtotal()
          );

      List<PurchaseProductResponse> responseBody = new ArrayList<>();
      for (PurchaseProduct purchaseProduct : purchaseProducts) {
        responseBody.add(PurchaseProductMapper.toResponse(purchaseProduct));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
