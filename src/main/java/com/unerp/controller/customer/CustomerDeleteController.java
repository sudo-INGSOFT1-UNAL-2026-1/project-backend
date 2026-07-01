 package com.unerp.controller.customer;

import com.unerp.service.customer.CustomerDeleteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerDeleteController {

  private final CustomerDeleteService customerDeleteService;

  public CustomerDeleteController(CustomerDeleteService customerDeleteService) {
    this.customerDeleteService = customerDeleteService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
    try {
      customerDeleteService.deleteCustomer(id);

      return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Customer cannot be deleted because it is associated with existing records");
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}