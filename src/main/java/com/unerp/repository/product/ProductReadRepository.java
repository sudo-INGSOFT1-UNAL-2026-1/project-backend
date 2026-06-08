package com.unerp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.product.Product;

public interface ProductReadRepository extends JpaRepository <Product, Integer> {

    boolean existsByName(String name);

    boolean existsByBatch(String batch);
}
