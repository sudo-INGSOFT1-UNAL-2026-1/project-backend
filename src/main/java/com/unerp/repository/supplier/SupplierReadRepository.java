package com.unerp.repository.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.supplier.Supplier;

public interface SupplierReadRepository extends JpaRepository <Supplier, Integer> {


    boolean existsByEmail(String email);

    Supplier findByEmail(String email);
}
