package com.unerp.controller.product;

import com.unerp.domain.product.Product;
import com.unerp.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Integer stock
    ) {
        try {
            Product product = productService.createProduct(
                    name,
                    description,
                    price,
                    stock
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(product);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}