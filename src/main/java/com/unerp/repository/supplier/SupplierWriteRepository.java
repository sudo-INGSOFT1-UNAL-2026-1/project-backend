package com.unerp.repository.supplier;

import com.unerp.domain.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierWriteRepository extends JpaRepository<Supplier, Integer> {}
