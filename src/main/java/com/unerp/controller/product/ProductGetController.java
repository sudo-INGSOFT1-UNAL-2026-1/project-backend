package com.unerp.controller.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.product.Product;
import com.unerp.service.product.ProductGetService;

@RestController
@RequestMapping("/product")
public class ProductGetController {

    private final ProductGetService productGetService;

    public ProductGetController(ProductGetService productGetService) {
        this.productGetService = productGetService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {

            List<Product> products = productGetService.getAllProducts();

            List<Map<String, Object>> responseBody = new ArrayList<>();

            for (Product product : products) {
                Map<String, Object> productData = new HashMap<>();
                productData.put("id", product.getId());
                productData.put("name", product.getName());
                productData.put("description", product.getDescription());
                productData.put("stock", product.getStock());
                productData.put("price", product.getPrice());
                productData.put("batch", product.getBatch());
                productData.put("expirationDate", product.getExpirationDate());
                productData.put("supplierId", product.getSupplierId());
                responseBody.add(productData);
            }

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

        }catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());

        }
    }

    @GetMapping("/search-by-id")
    public ResponseEntity<?> getProductById(@RequestParam Integer id) {
        try {

            Product product = productGetService.getProductById(id);

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

        }catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());

        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getProductsByParameters(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) Integer stock,
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) String batch,
        @RequestParam(required = false) LocalDate expirationDate,
        @RequestParam(required = false) Integer supplierId) {
        try {

            List<Product> products = productGetService.getProductsByParameters(name, description, stock, price, batch, expirationDate, supplierId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(products);

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

        }catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());

        }
    }
}
