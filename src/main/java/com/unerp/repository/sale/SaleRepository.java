package com.unerp.repository.sale;

import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findByStatus(SaleStatus status);
}