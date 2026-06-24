package com.unerp.service.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.customer.CustomerBuilder;
import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.customer.CustomerWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class CustomerCreateService {

  private final CustomerWriteRepository customerWriteRepository;
  private final AuthorizationService authorizationService;

  public CustomerCreateService(
      CustomerWriteRepository customerWriteRepository,
      AuthorizationService authorizationService) {
    this.customerWriteRepository = customerWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Customer createCustomer(String name, String address) {
    authorizationService.validatePermission(PermissionName.GESTION_VENTAS);

    validateName(name);

    Customer newCustomer =
        new CustomerBuilder()
            .setName(name)
            .setAddress(address)
            .build();

    return customerWriteRepository.save(newCustomer);
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Customer name is required");
    }
  }
}