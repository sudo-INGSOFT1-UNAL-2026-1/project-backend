package com.unerp.domain.purchase;

import java.time.LocalDate;

import com.unerp.domain.purchase.state.PurchaseState;

public class PurchaseBuilder {

    private Integer id;
    private Integer supplierId;
    private Integer userId;
    private LocalDate paymentDate;
    private LocalDate deliveryDate;
    private PurchaseState state;
    private Double totalCost;

    public PurchaseBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public PurchaseBuilder setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public PurchaseBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public PurchaseBuilder setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public PurchaseBuilder setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public PurchaseBuilder setState(PurchaseState state) {
        this.state = state;
        return this;
    }

    public PurchaseBuilder setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Purchase build() {
        return new Purchase(id, supplierId, userId, paymentDate, deliveryDate, state, totalCost);
    }
}
