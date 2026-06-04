package com.unerp.domain.permission;


import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class Permission{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    protected Permission() {
    }

    public Permission(Integer id, String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
