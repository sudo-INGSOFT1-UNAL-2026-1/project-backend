package com.unerp.repository.purchase;

import com.unerp.domain.purchase.Purchase;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.unerp.domain.purchase.state.PurchaseState;

public class PurchaseSpecifications {

  public static Specification<Purchase> filterBy(
      Integer supplierId,
      Integer userId,
      LocalDate paymentDate,
      LocalDate deliveryDate,
      PurchaseState state,
      BigDecimal totalCost) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (supplierId != null) {
        predicates.add(cb.equal(root.get("supplierId"), supplierId));
      }

      if (userId != null) {
        predicates.add(cb.equal(root.get("userId"), userId));
      }

      if (paymentDate != null) {
        predicates.add(cb.equal(root.get("paymentDate"), paymentDate));
      }

      if (deliveryDate != null) {
        predicates.add(cb.equal(root.get("deliveryDate"), deliveryDate));
      }

      if (state != null) {
        predicates.add(cb.equal(root.get("state"), state));
      }

      if (totalCost != null) {
        predicates.add(cb.equal(root.get("totalCost"), totalCost));
      }

      return cb.and(predicates.toArray(Predicate[]::new));
    };
  }
}