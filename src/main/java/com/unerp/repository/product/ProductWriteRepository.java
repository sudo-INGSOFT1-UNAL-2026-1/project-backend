package com.unerp.repository.product;

import com.unerp.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWriteRepository extends JpaRepository<Product, Integer> {

  @Override
  void deleteById(Integer id);

  void deleteBySupplierId(Integer supplierId);

  void deleteByBatch(String batch);
}
