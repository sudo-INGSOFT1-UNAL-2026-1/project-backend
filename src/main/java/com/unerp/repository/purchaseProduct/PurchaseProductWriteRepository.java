package com.unerp.repository.purchaseProduct;

import com.unerp.domain.purchaseProduct.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseProductWriteRepository extends JpaRepository<PurchaseProduct, Integer> {
}
