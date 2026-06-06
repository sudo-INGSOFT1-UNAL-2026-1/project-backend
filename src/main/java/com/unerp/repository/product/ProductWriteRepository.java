package com.unerp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.product.Product;

public interface ProductWriteRepository extends JpaRepository <Product, Integer> {
}
