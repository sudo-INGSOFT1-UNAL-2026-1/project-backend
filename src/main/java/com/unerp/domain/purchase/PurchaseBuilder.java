package com.unerp.domain.purchase;

import java.time.LocalDate;

public class PurchaseBuilder {

    private Integer id;
    private Integer supplierId;
    private Integer userId;
    private LocalDate paymentDate;
    private LocalDate deliveryDate;
    private String status;
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

    public PurchaseBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public PurchaseBuilder setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Purchase build() {
        return new Purchase(id, supplierId, userId, paymentDate, deliveryDate, status, totalCost);
    }
}
