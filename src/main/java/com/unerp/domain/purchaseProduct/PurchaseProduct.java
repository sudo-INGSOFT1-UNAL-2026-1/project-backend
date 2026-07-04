package com.unerp.domain.purchaseProduct;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_product")
public class PurchaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "purchase_id")
    private Integer purchaseId;

    @JoinColumn(name = "product_id")
    private Integer productId;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    private BigDecimal subtotal;

    public PurchaseProduct(
            Integer id,
            Integer purchaseId,
            Integer productId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public PurchaseProduct() {
    }

    public Integer getId() {
        return id;
    }
    public Integer getPurchaseId() {
        return purchaseId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}