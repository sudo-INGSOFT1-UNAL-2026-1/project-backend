package com.unerp.domain.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.unerp.domain.purchase.state.PurchaseState;
import com.unerp.domain.purchaseProduct.PurchaseProduct;

public class PurchaseBuilder {

    private Integer id;
    private Integer supplierId;
    private Integer userId;
    private LocalDate paymentDate;
    private LocalDate deliveryDate;
    private PurchaseState state;
    private BigDecimal totalCost;
    private List<PurchaseProduct> purchaseProducts;

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

    public PurchaseBuilder setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public PurchaseBuilder setPurchaseProducts(List<PurchaseProduct> purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
        return this;
    }

    public Purchase build() {
        return new Purchase(id, supplierId, userId, paymentDate, deliveryDate, state, totalCost, purchaseProducts);
    }
}
