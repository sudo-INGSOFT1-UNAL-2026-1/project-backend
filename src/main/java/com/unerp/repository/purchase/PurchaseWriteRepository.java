package com.unerp.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.purchase.Purchase;

public interface PurchaseWriteRepository extends JpaRepository<Purchase, Integer> {
  
  @Override
  void deleteById(Integer id);

  void deleteBySupplierId(Integer supplierId);
}
