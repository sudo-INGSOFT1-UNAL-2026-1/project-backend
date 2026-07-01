package com.unerp.service;

import com.unerp.domain.product.Product;
import com.unerp.repository.product.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

   
    public Product createProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        return productRepository.save(product);
    }

   
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }
    
    public void updateStock(Integer productId, Integer quantityChange) {
        Product product = getProductById(productId);
        int newStock = product.getStock() + quantityChange;
        if (newStock < 0) {
            throw new IllegalStateException("Stock insuficiente");
        }
        product.setStock(newStock);
        productRepository.save(product);
    }
}