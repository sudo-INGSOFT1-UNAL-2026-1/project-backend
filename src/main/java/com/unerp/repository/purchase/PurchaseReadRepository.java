package com.unerp.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.unerp.domain.purchase.Purchase;

public interface PurchaseReadRepository
    extends JpaRepository<Purchase, Integer>, JpaSpecificationExecutor<Purchase> {
}
