package com.unerp.controller.supplier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.supplier.Supplier;
import com.unerp.service.supplier.SupplierCreateService;

@RestController
@RequestMapping("/supplier")
public class SupplierCreateController {

    private final SupplierCreateService supplierCreateService;

    public SupplierCreateController(SupplierCreateService supplierCreateService) {
        this.supplierCreateService = supplierCreateService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSupplier(
        
            @RequestBody String name,
            @RequestBody Integer phone,
            @RequestBody String email
    ) {
        try { 

        Supplier supplier = supplierCreateService.createSupplier(
            name,
            phone,
            email
        );

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", supplier.getId());
        responseBody.put("name", supplier.getName());
        responseBody.put("phone", supplier.getPhone());
        responseBody.put("email", supplier.getEmail());

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