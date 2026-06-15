package com.unerp.repository.supplier;

import com.unerp.domain.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierReadRepository extends JpaRepository<Supplier, Integer> {

  boolean existsByEmail(String email);

  Supplier findByEmail(String email);
}
