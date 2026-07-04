package com.unerp.repository.purchaseProduct;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.unerp.domain.purchaseProduct.PurchaseProduct;

public interface PurchaseProductReadRepository
    extends JpaRepository<PurchaseProduct, Integer>, JpaSpecificationExecutor<PurchaseProduct> {

    public boolean existsByPurchaseIdAndProductId(Integer purchaseId, Integer productId);

    public List<PurchaseProduct> findAll(Specification<PurchaseProduct> filterBy);
}
