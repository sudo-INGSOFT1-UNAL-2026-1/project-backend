package com.unerp.controller.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.dto.supplier.SupplierMapper;
import com.unerp.dto.supplier.SupplierRequest;
import com.unerp.service.supplier.SupplierUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierUpdateController {

  private final SupplierUpdateService supplierUpdateService;

  public SupplierUpdateController(SupplierUpdateService supplierUpdateService) {
    this.supplierUpdateService = supplierUpdateService;
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateSupplier(
      @PathVariable Integer id,
      @RequestBody SupplierRequest request) {
    try {
      Supplier supplier = supplierUpdateService.updateSupplier(
          id,
          request.name(),
          request.phone(),
          request.email()
      );

      return ResponseEntity.status(HttpStatus.OK).body(SupplierMapper.toResponse(supplier));

    } catch (IllegalArgumentException e) {
      if ("Supplier doesn't exist".equals(e.getMessage())) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}