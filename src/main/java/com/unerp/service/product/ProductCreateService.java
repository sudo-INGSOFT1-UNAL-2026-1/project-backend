package com.unerp.service.product;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;

@Service
public class ProductCreateService {
    
    private final ProductReadRepository productReadRepository;
    private final SupplierReadRepository supplierReadRepository;
    private final ProductWriteRepository productWriteRepository;

    public ProductCreateService(ProductReadRepository productReadRepository,
        SupplierReadRepository supplierReadRepository,
        ProductWriteRepository productWriteRepository) {

        this.productReadRepository = productReadRepository;
        this.supplierReadRepository = supplierReadRepository;
        this.productWriteRepository = productWriteRepository;
    }

    public Product createProduct(
        String name,
        String description,
        Integer stock,
        Double price,
        String batch,
        LocalDate expirationDate,
        Integer supplierId
        ) {

        validateProductExists(name, batch);
        validateSupplierExists(supplierId);

        Product newProduct = new ProductBuilder()
            .setName(name)
            .setDescription(description)
            .setStock(stock)
            .setPrice(price)
            .setBatch(batch)
            .setExpirationDate(expirationDate)
            .setSupplierId(supplierId)
            .build();

        return productWriteRepository.save(newProduct);
    }

    private boolean validateNameExists(String name) {
        try {
            if (productReadRepository.existsByName(name)) {
                throw new IllegalArgumentException("Product with the same name already exists");
            }
            return false;
        }
        catch (IllegalArgumentException e) {
            return true;
        }
    }
    
    private boolean validateBatchExists(String batch) {
        try {
            if (productReadRepository.existsByBatch(batch)) {
                throw new IllegalArgumentException("Product with the same batch already exists");
            }
            return false;
        }
        catch (IllegalArgumentException e) {
            return true;
        }
    }

    private void validateProductExists(String name, String batch) {
        if (validateNameExists(name) && validateBatchExists(batch))
        {
            throw new IllegalArgumentException("Product with the same name and batch already exists");
        }
    }

    private void validateSupplierExists(Integer id) {
        if (!supplierReadRepository.existsById(id))
        {
            throw new IllegalArgumentException("Supplier doesn't exists");
        }
    }
}
