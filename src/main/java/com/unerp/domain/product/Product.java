package com.unerp.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

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

  @JoinColumn(name = "supplier_id")
  private Integer supplierId;

  public Product(
      Integer id,
      String name,
      String description,
      Integer stock,
      Double price,
      String batch,
      LocalDate expirationDate,
      Integer supplierId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.stock = stock;
    this.price = price;
    this.batch = batch;
    this.expirationDate = expirationDate;
    this.supplierId = supplierId;
  }

  public Product() {}

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

  public Integer getSupplierId() {
    return supplierId;
  }
}
