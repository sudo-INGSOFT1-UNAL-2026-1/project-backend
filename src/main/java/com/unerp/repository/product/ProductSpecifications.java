package com.unerp.repository.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.unerp.domain.product.Product;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecifications {

    public static Specification<Product> filterBy(
            String name,
            String description,
            Integer stock,
            Double price,
            String batch,
            LocalDate expirationDate,
            Integer supplierId) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                    )
                );
            }

            if (description != null) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%"
                    )
                );
            }

            if (stock != null) {
                predicates.add(
                    cb.equal(root.get("stock"), stock)
                );
            }

            if (price != null) {
                predicates.add(
                    cb.equal(root.get("price"), price)
                );
            }

            if (batch != null) {
                predicates.add(
                    cb.equal(root.get("batch"), batch)
                );
            }

            if (expirationDate != null) {
                predicates.add(
                    cb.equal(root.get("expirationDate"), expirationDate)
                );
            }

            if (supplierId != null) {
                predicates.add(
                    cb.equal(root.get("supplierId"), supplierId)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}