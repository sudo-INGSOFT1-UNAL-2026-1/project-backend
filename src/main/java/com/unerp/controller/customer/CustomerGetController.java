package com.unerp.controller.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.service.customer.CustomerGetService;
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
@RequestMapping("/customer")
public class CustomerGetController {

  private final CustomerGetService customerGetService;

  public CustomerGetController(CustomerGetService customerGetService) {
    this.customerGetService = customerGetService;
  }

  @GetMapping
  public ResponseEntity<?> getAllCustomers() {
    try {
      List<Map<String, Object>> responseBody =
          customerGetService.getAllCustomers().stream().map(this::buildCustomerResponse).toList();

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
    try {
      Customer customer = customerGetService.getCustomerById(id);

      return ResponseEntity.status(HttpStatus.OK).body(buildCustomerResponse(customer));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

  private Map<String, Object> buildCustomerResponse(Customer customer) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("id", customer.getId());
    responseBody.put("name", customer.getName());
    responseBody.put("address", customer.getAddress());

    return responseBody;
  }
}