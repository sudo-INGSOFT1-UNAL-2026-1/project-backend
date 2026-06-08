package com.unerp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unerp.domain.product.Product;

public interface ProductWriteRepository extends JpaRepository <Product, Integer> {

    void deleteById(Integer id);
    void deleteBySupplierId(Integer supplierId);
    void deleteByBatch(String batch);
}
