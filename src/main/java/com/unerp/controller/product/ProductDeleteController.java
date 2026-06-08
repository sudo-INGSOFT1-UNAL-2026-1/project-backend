package com.unerp.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.service.product.ProductDeleteService;

@RestController
@RequestMapping("/product")
public class ProductDeleteController {

    private final ProductDeleteService productDeleteService;

    public ProductDeleteController(ProductDeleteService productDeleteService) {
        this.productDeleteService = productDeleteService;
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteProductById(
        @RequestParam Integer id
    ) {
        try {
            productDeleteService.deleteProductById(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-batch")
    public ResponseEntity<?> deleteProductByBatch(
        @RequestParam String batch
    ) {
        try {
            productDeleteService.deleteProductByBatch(batch);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-supplier-id")
    public ResponseEntity<?> deleteProductBySupplierId(
        @RequestParam Integer supplierId
    ) {
        try {
            productDeleteService.deleteProductBySupplierId(supplierId);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }
}