package com.unerp.controller.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.dto.supplier.SupplierMapper;
import com.unerp.dto.supplier.SupplierResponse;
import com.unerp.service.supplier.SupplierGetService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierGetController {

  private final SupplierGetService supplierGetService;

  public SupplierGetController(SupplierGetService supplierGetService) {
    this.supplierGetService = supplierGetService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllSuppliers() {
    try {

      List<Supplier> suppliers = supplierGetService.getAllSuppliers();

      List<SupplierResponse> responseBody = new ArrayList<>();
      for (Supplier supplier : suppliers) {
        responseBody.add(SupplierMapper.toResponse(supplier));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @GetMapping("/update/{id}")
  public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
    try {
      Supplier supplier = supplierGetService.getSupplierById(id);

      return ResponseEntity.status(HttpStatus.OK).body(SupplierMapper.toResponse(supplier));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}