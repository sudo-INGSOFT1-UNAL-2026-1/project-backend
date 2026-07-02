package com.unerp.repository.purchaseProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public interface PurchaseProductReadRepository
    extends JpaRepository<PurchaseProduct, Integer>, JpaSpecificationExecutor<PurchaseProduct> {
}
