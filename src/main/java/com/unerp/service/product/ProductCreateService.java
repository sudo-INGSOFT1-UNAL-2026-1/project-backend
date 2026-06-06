package com.unerp.service.product;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;

@Service
public class ProductCreateService {
    
    private final ProductReadRepository productReadRepository;
    //private final SupplierReadRepository supplierReadRepository;
    private final ProductWriteRepository productWriteRepository;

    public ProductCreateService(ProductReadRepository productReadRepository,
        //SupplierReadRepository supplierReadRepository,
        ProductWriteRepository productWriteRepository) {
            
        this.productReadRepository = productReadRepository;
        //this.supplierReadRepository = supplierReadRepository;
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

        //validateSupplierExists(supplierId);

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

    
    //private void validateSupplierExists(Integer id) {
    //    if (! supplierReadRepository.existsById(id))
    //    {
    //        throw new IllegalArgumentException("Supplier doesn't exists");
    //    }
    //}


}
