package com.unerp.controller.product;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.product.Product;
import com.unerp.service.product.ProductCreateService;

@RestController
@RequestMapping("/product")
public class ProductCreateController {

    private final ProductCreateService productCreateService;

    public ProductCreateController(ProductCreateService productCreateService) {
        this.productCreateService = productCreateService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
        
            @RequestBody String name,
            @RequestBody String description,
            @RequestBody Integer stock,
            @RequestBody Double price,
            @RequestBody String batch,
            @RequestBody LocalDate expirationDate,
            @RequestBody Integer supplier_id
    ) {
        try { 

        Product product = productCreateService.createProduct(
            name,
            description,
            stock,
            price,
            batch,
            expirationDate,
            supplier_id
        );

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", product.getId());
        responseBody.put("name", product.getName());
        responseBody.put("description", product.getDescription());
        responseBody.put("stock", product.getStock());
        responseBody.put("price", product.getPrice());
        responseBody.put("batch", product.getBatch());
        responseBody.put("expirationDate", product.getExpirationDate());
        responseBody.put("supplier_id", product.getSupplier_id());

        return ResponseEntity
            .status(HttpStatus.CREATED)
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