package com.unerp.repository.product;

import com.unerp.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductReadRepository
    extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
  boolean existsByName(String name);

  boolean existsByBatch(String batch);
}
