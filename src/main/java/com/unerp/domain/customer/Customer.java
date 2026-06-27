package com.unerp.domain.customer;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Nullable 
    private String address;

    
    public Customer(Integer id, String name, @Nullable String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

  
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    
    public Customer() {}

    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}