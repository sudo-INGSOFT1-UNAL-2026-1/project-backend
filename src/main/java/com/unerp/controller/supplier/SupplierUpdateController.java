package com.unerp.controller.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.service.supplier.SupplierUpdateService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) String email) {
    try {
      Supplier supplier = supplierUpdateService.updateSupplier(id, name, phone, email);

      return ResponseEntity.status(HttpStatus.OK).body(buildSupplierResponse(supplier));
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

  private Map<String, Object> buildSupplierResponse(Supplier supplier) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("id", supplier.getId());
    responseBody.put("name", supplier.getName());
    responseBody.put("phone", supplier.getPhone());
    responseBody.put("email", supplier.getEmail());

    return responseBody;
  }
}