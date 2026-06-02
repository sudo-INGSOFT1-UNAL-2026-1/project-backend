package com.unerp.service.product;

import com.unerp.domain.product.Product;
import com.unerp.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(
            String name,
            String description,
            Double price,
            Integer stock
    ) {

        Product product = new Product(
                null,
                name,
                description,
                price,
                stock,
                true
        );

        return productRepository.save(product);
    }
}
