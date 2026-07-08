package com.unerp.repository.purchaseProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Integer> {
}