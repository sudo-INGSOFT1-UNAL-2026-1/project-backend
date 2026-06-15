package com.unerp.domain.purchaseProduct;

public class PurchaseProductBuilder {

    private Integer id;
    private Integer purchaseId;
    private Integer productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;

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

    public PurchaseProductBuilder setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public PurchaseProductBuilder setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public PurchaseProduct build() {
        return new PurchaseProduct(id, purchaseId, productId, quantity, unitPrice, subtotal);
    }
}
