package com.unerp.domain.sale;

import com.fasterxml.jackson.annotation.JsonManagedReference; 
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "customer_id")
    private Integer customerId;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    @Column(name = "status")
    private SaleStatus status;
    
    @Column(name = "total_cost")
    private BigDecimal totalCost = BigDecimal.ZERO;
    
    private String description;
    
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SaleProduct> products = new ArrayList<>();
    
    public Sale() {}
    
 
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public SaleStatus getStatus() { return status; }
    public void setStatus(SaleStatus status) { this.status = status; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<SaleProduct> getProducts() { return products; }
    public void setProducts(List<SaleProduct> products) { this.products = products; }
    
}