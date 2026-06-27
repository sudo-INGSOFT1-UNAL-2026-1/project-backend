package com.unerp.domain.product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductBuilder {

  private Integer id;
  private String name;
  private String description;
  private Integer stock;
  private BigDecimal price;
  private String batch;
  private LocalDate expirationDate;
  private Integer supplierId;

  public ProductBuilder setId(Integer id) {
    this.id = id;
    return this;
  }

  public ProductBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public ProductBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public ProductBuilder setStock(Integer stock) {
    this.stock = stock;
    return this;
  }

  public ProductBuilder setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public ProductBuilder setBatch(String batch) {
    this.batch = batch;
    return this;
  }

  public ProductBuilder setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  public ProductBuilder setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
    return this;
  }

  public Product build() {
    return new Product(id, name, description, stock, price, batch, expirationDate, supplierId);
  }
}