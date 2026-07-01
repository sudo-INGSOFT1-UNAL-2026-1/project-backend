package com.unerp.controller.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.service.supplier.SupplierGetService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @GetMapping
  public ResponseEntity<?> getAllSuppliers() {
    try {
      List<Map<String, Object>> responseBody =
          supplierGetService.getAllSuppliers().stream().map(this::buildSupplierResponse).toList();

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
    try {
      Supplier supplier = supplierGetService.getSupplierById(id);

      return ResponseEntity.status(HttpStatus.OK).body(buildSupplierResponse(supplier));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  private Map<String, Object> buildSupplierResponse(Supplier supplier) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("id", supplier.getId());
    responseBody.put("name", supplier.getName());
    responseBody.put("phone", supplier.getPhone());
    responseBody.put("email", supplier.getEmail());

    return responseBody;
  }
}