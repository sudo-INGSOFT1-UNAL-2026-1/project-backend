package com.unerp.controller.supplier;

import com.unerp.service.supplier.SupplierDeleteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierDeleteController {

  private final SupplierDeleteService supplierDeleteService;

  public SupplierDeleteController(SupplierDeleteService supplierDeleteService) {
    this.supplierDeleteService = supplierDeleteService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
    try {
      supplierDeleteService.deleteSupplier(id);

      return ResponseEntity.status(HttpStatus.OK).body("Supplier deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Supplier cannot be deleted because it is associated with existing records");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}