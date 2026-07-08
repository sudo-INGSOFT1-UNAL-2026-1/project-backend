package com.unerp.repository.purchaseProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public interface PurchaseProductWriteRepository extends JpaRepository<PurchaseProduct, Integer> {

    public void deleteByPurchaseId(Integer purchaseId);

    public void deleteByProductId(Integer productId);
}
