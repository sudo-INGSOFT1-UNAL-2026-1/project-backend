package com.unerp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SaleRequestDTO {
    private Integer customerId;
    private String customerName;
    private String customerAddress;
    private String description;
    private LocalDate deliveryDate;
    private List<SaleProductDTO> products;

    
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public List<SaleProductDTO> getProducts() { return products; }
    public void setProducts(List<SaleProductDTO> products) { this.products = products; }
}

class SaleProductDTO {
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    
  
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}