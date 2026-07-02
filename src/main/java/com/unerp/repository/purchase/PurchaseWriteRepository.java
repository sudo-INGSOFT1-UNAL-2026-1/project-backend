package com.unerp.repository.purchase;

import com.unerp.domain.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseWriteRepository extends JpaRepository<Purchase, Integer> {
}
