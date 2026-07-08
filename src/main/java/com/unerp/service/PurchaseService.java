package com.unerp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unerp.domain.purchase.Purchase;
import com.unerp.domain.purchaseProduct.PurchaseProduct;
import com.unerp.repository.purchase.PurchaseRepository;
import com.unerp.repository.purchaseProduct.PurchaseProductRepository;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    
    public PurchaseService(
        PurchaseRepository purchaseRepository,
        PurchaseProductRepository purchaseProductRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseProductRepository = purchaseProductRepository;
    }

   
    public Purchase createPurchase(Purchase purchase) {
        if (purchase.getSupplierId() == null) {
            throw new IllegalArgumentException("El ID del proveedor es requerido");
        }
        if (purchase.getUserId() == null) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        return purchaseRepository.save(purchase);
    }

   
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
    
    public Purchase getPurchaseById(Integer id) {
        return purchaseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada"));
    }

    public PurchaseProduct createPurchaseProduct(PurchaseProduct purchase) {
        if (purchase.getPurchaseId() == null) {
            throw new IllegalArgumentException("El ID de la compra es requerido");
        }
        if (purchase.getProductId() == null) {
            throw new IllegalArgumentException("El ID del producto es requerido");
        }
        return purchaseProductRepository.save(purchase);
    }

   
    public List<PurchaseProduct> getAllPurchaseProducts() {
        return purchaseProductRepository.findAll();
    }
    
    public PurchaseProduct getPurchaseProductById(Integer id) {
        return purchaseProductRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto de compra no encontrado"));
    }
}