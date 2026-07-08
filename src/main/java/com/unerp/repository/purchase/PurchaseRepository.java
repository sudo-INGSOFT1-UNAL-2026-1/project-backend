package com.unerp.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.purchase.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
}