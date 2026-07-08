package com.unerp.controller.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.dto.supplier.SupplierRequest;
import com.unerp.dto.supplier.SupplierMapper;
import com.unerp.service.supplier.SupplierCreateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierCreateController {

  private final SupplierCreateService supplierCreateService;

  public SupplierCreateController(SupplierCreateService supplierCreateService) {
    this.supplierCreateService = supplierCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createSupplier(
      @Valid @RequestBody SupplierRequest request){
    try {

      Supplier supplier = supplierCreateService.createSupplier(
          request.name(),
          request.phone(),
          request.email()
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(SupplierMapper.toResponse(supplier));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
