package com.unerp.domain.product;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Integer stock;
    
    private Double price;

    private String batch;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Integer supplier_id;

    public Product(
            Integer id,
            String name,
            String description,
            Integer stock,
            Double price,
            String batch,
            LocalDate expirationDate,
            Integer supplier_id
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.batch = batch;
        this.expirationDate = expirationDate;
        this.supplier_id = supplier_id;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getBatch() {
        return batch;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

}
