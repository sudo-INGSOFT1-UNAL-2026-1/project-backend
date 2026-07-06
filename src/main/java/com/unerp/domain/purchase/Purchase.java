package com.unerp.domain.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchase.state.PurchaseState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Transient private PurchaseState state;

    @Column(name = "status")
    private String stateString;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseProduct> purchaseProducts;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    public Purchase(
            Integer id,
            Integer supplierId,
            Integer userId,
            LocalDate paymentDate,
            LocalDate deliveryDate,
            PurchaseState state,
            BigDecimal totalCost,
            List<PurchaseProduct> purchaseProducts
    ) {
        this.id = id;
        this.supplierId = supplierId;
        this.userId = userId;
        this.paymentDate = paymentDate;
        this.deliveryDate = deliveryDate;
        this.state = state;
        this.stateString = state.getName();
        this.totalCost = totalCost;
        this.purchaseProducts = purchaseProducts;
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

    public List<PurchaseProduct> getPurchaseProducts() {
        return purchaseProducts;
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