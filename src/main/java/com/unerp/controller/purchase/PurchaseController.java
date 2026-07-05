package com.unerp.controller.purchase;

import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class PurchaseController {
    
    private final PurchaseService purchaseService;
    
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> createPurchaseProduct(@RequestBody PurchaseProduct purchaseProduct) {
        try {
            PurchaseProduct created = purchaseService.createPurchaseProduct(purchaseProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(purchaseService.getAllProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Product product = purchaseService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}