package com.unerp.domain.supplier;

public class SupplierBuilder {
    
    private Integer id;
    private String name;
    private Integer phone;
    private String email;

    public SupplierBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public SupplierBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SupplierBuilder setPhone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public SupplierBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Supplier build() {
        return new Supplier(id, name, phone, email);
    }
}
