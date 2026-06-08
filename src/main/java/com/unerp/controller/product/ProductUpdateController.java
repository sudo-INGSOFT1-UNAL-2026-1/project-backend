package com.unerp.controller.product;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.product.Product;
import com.unerp.service.product.ProductUpdateService;

@RestController
@RequestMapping("/product")
public class ProductUpdateController {

    private final ProductUpdateService productUpdateService;

    public ProductUpdateController(ProductUpdateService productUpdateService) {
        this.productUpdateService = productUpdateService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(
        @RequestParam Integer id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Integer stock,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) String batch,
        @RequestParam(required = false) LocalDate expirationDate,
        @RequestParam(required = false) Integer supplierId
    ) {
        try { 

        Product product = productUpdateService.updateProduct(
            id,
            name,
            description,
            stock,
            price,
            batch,
            expirationDate,
            supplierId
        );

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", product.getId());
        responseBody.put("name", product.getName());
        responseBody.put("description", product.getDescription());
        responseBody.put("stock", product.getStock());
        responseBody.put("price", product.getPrice());
        responseBody.put("batch", product.getBatch());
        responseBody.put("expirationDate", product.getExpirationDate());
        responseBody.put("supplierId", product.getSupplierId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseBody);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (SecurityException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());

        }
    }
}