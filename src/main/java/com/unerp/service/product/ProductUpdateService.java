package com.unerp.service.product;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;

@Service
public class ProductUpdateService {
    
    private final ProductReadRepository productReadRepository;
    private final ProductWriteRepository productWriteRepository;

    public ProductUpdateService(ProductReadRepository productReadRepository,
        ProductWriteRepository productWriteRepository) {

        this.productReadRepository = productReadRepository;
        this.productWriteRepository = productWriteRepository;
    }

    public Product updateProduct(
        Integer id,
        String name,
        String description,
        Integer stock,
        Double price,
        String batch,
        LocalDate expirationDate,
        Integer supplierId
        ) {

        Product existingProduct = productReadRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product doesn't exist"));

        String existingName = existingProduct.getName();
        String existingDescription = existingProduct.getDescription();
        Integer existingStock = existingProduct.getStock();
        Double existingPrice = existingProduct.getPrice().doubleValue();
        String existingBatch = existingProduct.getBatch();
        LocalDate existingExpirationDate = existingProduct.getExpirationDate();
        Integer existingSupplierId = existingProduct.getSupplierId();

        Product updatedProduct = new ProductBuilder()
            .setId(id)
            .setName(name != null ? name : existingName)
            .setDescription(description != null ? description : existingDescription)
            .setStock(stock != null ? stock : existingStock)
            .setPrice(price != null ? price : existingPrice)
            .setBatch(batch != null ? batch : existingBatch)
            .setExpirationDate(expirationDate != null ? expirationDate : existingExpirationDate)
            .setSupplierId(supplierId != null ? supplierId : existingSupplierId)
            .build();
        return productWriteRepository.save(updatedProduct);
    }
}
