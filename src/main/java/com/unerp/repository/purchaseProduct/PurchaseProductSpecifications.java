package com.unerp.repository.purchaseProduct;

import com.unerp.domain.purchaseProduct.PurchaseProduct;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class PurchaseProductSpecifications {

  public static Specification<PurchaseProduct> filterBy(
      Integer purchaseId,
      Integer productId,
      Integer quantity,
      BigDecimal unitPrice,
      BigDecimal subtotal) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (purchaseId != null) {
        predicates.add(cb.equal(root.get("purchaseId"), purchaseId));
      }

      if (productId != null) {
        predicates.add(cb.equal(root.get("productId"), productId));
      }

      if (quantity != null) {
        predicates.add(cb.equal(root.get("quantity"), quantity));
      }

      if (unitPrice != null) {
        predicates.add(cb.equal(root.get("unitPrice"), unitPrice));
      }

      if (subtotal != null) {
        predicates.add(cb.equal(root.get("subtotal"), subtotal));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}