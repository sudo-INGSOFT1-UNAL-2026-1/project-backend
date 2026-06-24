package com.unerp.controller.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.service.customer.CustomerUpdateService;
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
@RequestMapping("/customer")
public class CustomerUpdateController {

  private final CustomerUpdateService customerUpdateService;

  public CustomerUpdateController(CustomerUpdateService customerUpdateService) {
    this.customerUpdateService = customerUpdateService;
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCustomer(
      @PathVariable Integer id,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String address) {
    try {
      Customer customer = customerUpdateService.updateCustomer(id, name, address);

      return ResponseEntity.status(HttpStatus.OK).body(buildCustomerResponse(customer));
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

  private Map<String, Object> buildCustomerResponse(Customer customer) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("id", customer.getId());
    responseBody.put("name", customer.getName());
    responseBody.put("address", customer.getAddress());

    return responseBody;
  }
}