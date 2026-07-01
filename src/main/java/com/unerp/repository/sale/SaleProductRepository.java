package com.unerp.repository.sale;

import com.unerp.domain.sale.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Integer> {
}