package com.unerp.domain.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.unerp.domain.purchase.state.PurchaseState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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

    @Transient private PurchaseState state;

    @Column(name = "status")
    private String stateString;
    
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    public Purchase(
            Integer id,
            Integer supplierId,
            Integer userId,
            LocalDate paymentDate,
            LocalDate deliveryDate,
            PurchaseState state,
            BigDecimal totalCost
    ) {
        this.id = id;
        this.supplierId = supplierId;
        this.userId = userId;
        this.paymentDate = paymentDate;
        this.deliveryDate = deliveryDate;
        this.state = state;
        this.stateString = state.getName();
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

    public PurchaseState getState() {
        return state;
    }

    public void pay() {
        this.state = this.state.paid();
        this.stateString = this.state.getName();
    }

    public void pending() {
        this.state = this.state.pending();
        this.stateString = this.state.getName();
    }

    public void receive() {
        this.state = this.state.received();
        this.stateString = this.state.getName();
    }

    public void cancel() {
        this.state = this.state.canceled();
        this.stateString = this.state.getName();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}