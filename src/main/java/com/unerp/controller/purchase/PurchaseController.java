package com.unerp.controller.purchase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.service.PurchaseService;

@RestController
@RequestMapping("/compras")
public class PurchaseController {
    
    private final PurchaseService purchaseService;
    
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
        try {
            Purchase created = purchaseService.createPurchase(purchase);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseById(@PathVariable Integer id) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(id);
            return ResponseEntity.ok(purchase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/productos/crear")
    public ResponseEntity<?> createPurchaseProduct(@RequestBody PurchaseProduct purchaseProduct) {
        try {
            PurchaseProduct created = purchaseService.createPurchaseProduct(purchaseProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/productos/listar")
    public ResponseEntity<List<PurchaseProduct>> getAllPurchaseProducts() {
        return ResponseEntity.ok(purchaseService.getAllPurchaseProducts());
    }
    
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getPurchaseProductById(@PathVariable Integer id) {
        try {
            PurchaseProduct purchaseProduct = purchaseService.getPurchaseProductById(id);
            return ResponseEntity.ok(purchaseProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}