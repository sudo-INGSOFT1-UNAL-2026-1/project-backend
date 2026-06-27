package com.unerp.service;

import com.unerp.domain.product.Product;
import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleProduct;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.repository.sale.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SaleService saleService;

    
    @Test
    public void shouldCreateSaleWhenStockIsSufficient() {
        
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop Gamer");
        product.setStock(10);
        product.setPrice(new BigDecimal("2500000"));

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setProductId(1);
        saleProduct.setQuantity(5);
        saleProduct.setUnitPrice(new BigDecimal("2450000"));

        Sale sale = new Sale();
        sale.setCustomerId(1);
        sale.setStatus(SaleStatus.por_enviar);
        sale.setProducts(List.of(saleProduct));

        when(productService.getProductById(1)).thenReturn(product);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        
        Sale result = saleService.createSale(sale);

        
        assertNotNull(result);
        verify(productService, times(1)).updateStock(eq(1), eq(-5));
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    
    @Test
    public void shouldRejectSaleWhenStockIsInsufficient() {
        
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop Gamer");
        product.setStock(10);
        product.setPrice(new BigDecimal("2500000"));

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setProductId(1);
        saleProduct.setQuantity(15);  
        saleProduct.setUnitPrice(new BigDecimal("2450000"));

        Sale sale = new Sale();
        sale.setCustomerId(1);
        sale.setStatus(SaleStatus.por_enviar);
        sale.setProducts(List.of(saleProduct));

        when(productService.getProductById(1)).thenReturn(product);

        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            saleService.createSale(sale);
        });

        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    
    @Test
    public void shouldRejectSaleWhenStockIsZero() {
        
        Product product = new Product();
        product.setId(2);
        product.setName("Producto Sin Stock");
        product.setStock(0);  
        product.setPrice(new BigDecimal("50000"));

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setProductId(2);
        saleProduct.setQuantity(1);
        saleProduct.setUnitPrice(new BigDecimal("50000"));

        Sale sale = new Sale();
        sale.setCustomerId(1);
        sale.setStatus(SaleStatus.por_enviar);
        sale.setProducts(List.of(saleProduct));

        when(productService.getProductById(2)).thenReturn(product);

        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            saleService.createSale(sale);
        });

        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        verify(saleRepository, never()).save(any(Sale.class));
    }

    
    @Test
    public void shouldCalculateCorrectTotal() {
        
        Product product1 = new Product();
        product1.setId(1);
        product1.setStock(10);

        Product product2 = new Product();
        product2.setId(2);
        product2.setStock(50);

        SaleProduct saleProduct1 = new SaleProduct();
        saleProduct1.setProductId(1);
        saleProduct1.setQuantity(2);
        saleProduct1.setUnitPrice(new BigDecimal("2450000"));

        SaleProduct saleProduct2 = new SaleProduct();
        saleProduct2.setProductId(2);
        saleProduct2.setQuantity(5);
        saleProduct2.setUnitPrice(new BigDecimal("140000"));

        Sale sale = new Sale();
        sale.setCustomerId(1);
        sale.setStatus(SaleStatus.por_enviar);
        sale.setProducts(List.of(saleProduct1, saleProduct2));

        when(productService.getProductById(1)).thenReturn(product1);
        when(productService.getProductById(2)).thenReturn(product2);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        
        Sale result = saleService.createSale(sale);

        
        BigDecimal expectedTotal = new BigDecimal("5600000");
        assertEquals(expectedTotal, result.getTotalCost());
    }
}