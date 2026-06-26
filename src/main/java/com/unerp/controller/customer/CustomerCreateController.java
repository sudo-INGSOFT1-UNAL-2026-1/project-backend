package com.unerp.controller.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.dto.customer.CustomerCreateRequest;
import com.unerp.dto.customer.CustomerMapper;
import com.unerp.dto.customer.CustomerResponse;
import com.unerp.service.customer.CustomerCreateService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerCreateController {

  private final CustomerCreateService customerCreateService;

  public CustomerCreateController(CustomerCreateService customerCreateService) {
    this.customerCreateService = customerCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createCustomer(
      @Valid @RequestBody CustomerCreateRequest request) {
    try {
      Customer customer = customerCreateService.createCustomer(request.name(), request.address());

      return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toResponse(customer));

    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}