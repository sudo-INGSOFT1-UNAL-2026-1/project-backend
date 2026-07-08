package com.unerp.service;



import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleProduct;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.repository.sale.SaleRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

public class SaleService {
    
    private final SaleRepository saleRepository;
    private final ProductService productService;
    
    public SaleService(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }
    
    @Transactional
    public Sale createSale(Sale sale) {
        
        for (SaleProduct sp : sale.getProducts()) {
            var product = productService.getProductById(sp.getProductId());
            if (product.getStock() < sp.getQuantity()) {
                throw new IllegalStateException(
                    "Stock insuficiente para producto ID: " + sp.getProductId()
                );
            }
        }
        
        
        BigDecimal total = sale.getProducts().stream()
            .map(sp -> sp.getUnitPrice().multiply(BigDecimal.valueOf(sp.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        sale.setTotalCost(total);
        
       
        Sale savedSale = saleRepository.save(sale);
        
        
        for (SaleProduct sp : sale.getProducts()) {
            productService.updateStock(sp.getProductId(), -sp.getQuantity());
        }
        
        return savedSale;
    }
    
    @Transactional
    public Sale updateSale(Integer id, Sale saleDetails) {
        Sale existing = saleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
        
        if (existing.getStatus() == SaleStatus.entregado || 
            existing.getStatus() == SaleStatus.cancelado) {
            throw new IllegalStateException("No se puede modificar una venta entregada o cancelada");
        }
        
        existing.setDeliveryDate(saleDetails.getDeliveryDate());
        existing.setDescription(saleDetails.getDescription());
        existing.setStatus(saleDetails.getStatus());
        
        return saleRepository.save(existing);
    }
    
    @Transactional
    public void deleteSale(Integer id) {
        Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
        
        if (sale.getStatus() == SaleStatus.entregado) {
            throw new IllegalStateException("No se puede eliminar una venta ya entregada");
        }
        
        
        for (SaleProduct sp : sale.getProducts()) {
            productService.updateStock(sp.getProductId(), sp.getQuantity());
        }
        
        saleRepository.delete(sale);
    }
    
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
    
    public Sale getSaleById(Integer id) {
        return saleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
    }
}