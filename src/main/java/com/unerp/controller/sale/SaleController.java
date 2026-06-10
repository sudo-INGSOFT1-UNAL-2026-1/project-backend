package com.unerp.controller.sale;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleProduct;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.repository.customer.CustomerRepository;
import com.unerp.repository.sale.SaleRepository;
import com.unerp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ventas")
public class SaleController {
    
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    
    public SaleController(SaleRepository saleRepository, 
                          CustomerRepository customerRepository,
                          ProductService productService) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
    }
    
    @PostMapping("/cotizacion")
    public ResponseEntity<?> createQuotation(@RequestBody Map<String, Object> request) {
        try {
           
            Integer customerId = (Integer) request.get("customerId");
            String customerName = (String) request.get("customerName");
            String customerAddress = (String) request.get("customerAddress");
            String description = (String) request.get("description");
            String deliveryDateStr = (String) request.get("deliveryDate");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> productsData = (List<Map<String, Object>>) request.get("products");
            
          
            if (customerId == null && customerName != null) {
                Customer newCustomer = new Customer(customerName, customerAddress);
                Customer saved = customerRepository.save(newCustomer);
                customerId = saved.getId();
            }
            
            if (customerId == null) {
                return ResponseEntity.badRequest().body("Se requiere customerId o customerName");
            }
            
          
            Sale sale = new Sale();
            sale.setCustomerId(customerId);
            sale.setDescription(description);
            sale.setDeliveryDate(LocalDate.parse(deliveryDateStr));
            sale.setStatus(SaleStatus.por_enviar);
            sale.setTotalCost(BigDecimal.ZERO);
            
            Sale savedSale = saleRepository.save(sale);
            
            
            BigDecimal total = BigDecimal.ZERO;
            List<SaleProduct> products = new ArrayList<>();
            
            for (Map<String, Object> prodData : productsData) {
                Integer productId = (Integer) prodData.get("productId");
                Integer quantity = (Integer) prodData.get("quantity");
                BigDecimal unitPrice = new BigDecimal(prodData.get("unitPrice").toString());
                
                
                var product = productService.getProductById(productId);
                if (product.getStock() < quantity) {
                    return ResponseEntity.badRequest().body(
                        "Stock insuficiente para producto: " + product.getName()
                    );
                }
                
               
                SaleProduct sp = new SaleProduct();
                sp.setSale(savedSale);  
                sp.setProductId(productId);
                sp.setQuantity(quantity);
                sp.setUnitPrice(unitPrice);
                sp.calculateSubtotal();
                
                products.add(sp);
                
               
                productService.updateStock(productId, -quantity);
                
                total = total.add(sp.getSubtotal());
            }
            
           
            savedSale.setProducts(products);
            savedSale.setTotalCost(total);
            saleRepository.save(savedSale);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        try {
            Sale existing = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
            
            if (existing.getStatus() == SaleStatus.entregado || 
                existing.getStatus() == SaleStatus.cancelado) {
                return ResponseEntity.badRequest().body("No se puede modificar una venta entregada o cancelada");
            }
            
            if (updates.containsKey("deliveryDate")) {
                existing.setDeliveryDate(LocalDate.parse((String) updates.get("deliveryDate")));
            }
            if (updates.containsKey("description")) {
                existing.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("status")) {
                existing.setStatus(SaleStatus.valueOf((String) updates.get("status")));
            }
            
            Sale updated = saleRepository.save(existing);
            return ResponseEntity.ok(updated);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Integer id) {
        try {
            Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
            
            if (sale.getStatus() == SaleStatus.entregado) {
                return ResponseEntity.badRequest().body("No se puede eliminar una venta ya entregada");
            }
            
            
            for (SaleProduct sp : sale.getProducts()) {
                productService.updateStock(sp.getProductId(), sp.getQuantity());
            }
            
            saleRepository.delete(sale);
            return ResponseEntity.ok("Cotización eliminada exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        try {
            Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
            
            SaleStatus newStatus = SaleStatus.valueOf(status);
            sale.setStatus(newStatus);
            Sale updated = saleRepository.save(sale);
            return ResponseEntity.ok(updated);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido. Use: entregado, por_enviar, en_camino, cancelado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Integer id) {
        try {
            Sale sale = saleRepository.findById(id).orElse(null);
            if (sale == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sale);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/estado/{status}")
    public ResponseEntity<?> getSalesByStatus(@PathVariable String status) {
        try {
            SaleStatus saleStatus = SaleStatus.valueOf(status);
            return ResponseEntity.ok(saleRepository.findByStatus(saleStatus));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido");
        }
    }
}
    
  