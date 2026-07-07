package com.unerp.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.customer.Customer;
import com.unerp.dto.customer.CustomerMapper;
import com.unerp.dto.customer.CustomerUpdateRequest;
import com.unerp.service.customer.CustomerUpdateService;

@RestController
@RequestMapping("/customer")
public class CustomerUpdateController {

  private final CustomerUpdateService customerUpdateService;

  public CustomerUpdateController(CustomerUpdateService customerUpdateService) {
    this.customerUpdateService = customerUpdateService;
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<?> updateCustomer(
      @PathVariable Integer id,
      @RequestBody CustomerUpdateRequest request) {
    try {
      Customer customer = customerUpdateService.updateCustomer(id, request.name(), request.address());

      return ResponseEntity.status(HttpStatus.OK).body(CustomerMapper.toResponse(customer));
    } catch (IllegalArgumentException e) {
      if ("Customer doesn't exist".equals(e.getMessage())) {
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
