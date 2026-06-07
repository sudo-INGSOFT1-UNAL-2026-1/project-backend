package com.unerp.repository.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.supplier.Supplier;

public interface SupplierWriteRepository extends JpaRepository <Supplier, Integer> {
}
