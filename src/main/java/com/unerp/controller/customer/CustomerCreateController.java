package com.unerp.controller.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.service.customer.CustomerCreateService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
      @RequestParam String name,
      @RequestParam(required = false) String address) {
    try {
      Customer customer = customerCreateService.createCustomer(name, address);

      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("id", customer.getId());
      responseBody.put("name", customer.getName());
      responseBody.put("address", customer.getAddress());

      return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}