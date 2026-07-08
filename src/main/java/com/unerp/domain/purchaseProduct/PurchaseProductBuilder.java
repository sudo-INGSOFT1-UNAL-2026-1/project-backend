package com.unerp.domain.purchaseProduct;

import java.math.BigDecimal;

public class PurchaseProductBuilder {

    private Integer id;
    private Integer purchaseId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public PurchaseProductBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public PurchaseProductBuilder setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    public PurchaseProductBuilder setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public PurchaseProductBuilder setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public PurchaseProductBuilder setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public PurchaseProductBuilder setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public PurchaseProduct build() {
        return new PurchaseProduct(id, purchaseId, productId, quantity, unitPrice, subtotal);
    }
}
