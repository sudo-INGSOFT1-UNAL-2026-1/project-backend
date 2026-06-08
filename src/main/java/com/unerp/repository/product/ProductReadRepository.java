package com.unerp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.unerp.domain.product.Product;

public interface ProductReadRepository extends JpaRepository <Product, Integer>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);
    boolean existsByBatch(String batch);
}
