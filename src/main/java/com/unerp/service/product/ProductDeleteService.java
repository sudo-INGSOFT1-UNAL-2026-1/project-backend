package com.unerp.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;

@Service
@Transactional
public class ProductDeleteService {
    
    private final ProductReadRepository productReadRepository;
    private final SupplierReadRepository supplierReadRepository;
    private final ProductWriteRepository productWriteRepository;

    public ProductDeleteService(ProductReadRepository productReadRepository,
        SupplierReadRepository supplierReadRepository,
        ProductWriteRepository productWriteRepository) {

        this.productReadRepository = productReadRepository;
        this.supplierReadRepository = supplierReadRepository;
        this.productWriteRepository = productWriteRepository;
    }

    public void deleteProductById(Integer id) {
        if (!productReadRepository.existsById(id)) {
            throw new IllegalArgumentException("Product doesn't exist");
        }
        productWriteRepository.deleteById(id);
    }

    public void deleteProductByBatch(String batch) {
        if (!productReadRepository.existsByBatch(batch)) {
            throw new IllegalArgumentException("Batch doesn't exist");
        }
        productWriteRepository.deleteByBatch(batch);
    }

    public void deleteProductBySupplierId(Integer supplierId) {
        if (!supplierReadRepository.existsById(supplierId)) {
            throw new IllegalArgumentException("Supplier doesn't exist");
        }
        productWriteRepository.deleteBySupplierId(supplierId);
    }
}
