package com.unerp.domain.purchase;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "supplier_id")
    private Integer supplierId;

    @JoinColumn(name = "user_id")
    private Integer userId;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    private String status;
    
    @Column(name = "total_cost")
    private Double totalCost;

    public Purchase(
            Integer id,
            Integer supplierId,
            Integer userId,
            LocalDate paymentDate,
            LocalDate deliveryDate,
            String status,
            Double totalCost
    ) {
        this.id = id;
        this.supplierId = supplierId;
        this.userId = userId;
        this.paymentDate = paymentDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.totalCost = totalCost;
    }

    public Purchase() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Double getTotalCost() {
        return totalCost;
    }
}