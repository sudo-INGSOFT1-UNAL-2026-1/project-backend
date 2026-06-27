package com.unerp.domain.customer;

public class CustomerBuilder {

  private Integer id;
  private String name;
  private String address;

  public CustomerBuilder setId(Integer id) {
    this.id = id;
    return this;
  }

  public CustomerBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public CustomerBuilder setAddress(String address) {
    this.address = address;
    return this;
  }

  public Customer build() {
    return new Customer(id, name, address);
  }
}