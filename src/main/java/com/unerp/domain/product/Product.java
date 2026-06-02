package com.unerp.domain.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private Boolean active;

    public Product() {
    }

    public Product(
            Integer id,
            String name,
            String description,
            Double price,
            Integer stock,
            Boolean active
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
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

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getActive() {
        return active;
    }

    public void updateInformation(
            String name,
            String description,
            Double price
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void updateStock(Integer stock) {
        this.stock = stock;
    }

    public void deactivate() {
        this.active = false;
    }
}