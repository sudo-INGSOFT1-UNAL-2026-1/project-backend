package com.unerp.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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

  private BigDecimal price;

  private String batch;

  @Column(name = "expiration_date")
  private LocalDate expirationDate;

  @Column(name = "supplier_id")
  private Integer supplierId;

  public Product() {}

  public Product(
      Integer id,
      String name,
      String description,
      Integer stock,
      BigDecimal price,
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

  public BigDecimal getPrice() {
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

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }
}