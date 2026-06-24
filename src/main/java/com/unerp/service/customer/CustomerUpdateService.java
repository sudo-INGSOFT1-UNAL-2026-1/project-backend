package com.unerp.service.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.customer.CustomerBuilder;
import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.customer.CustomerReadRepository;
import com.unerp.repository.customer.CustomerWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class CustomerUpdateService {

  private final CustomerReadRepository customerReadRepository;
  private final CustomerWriteRepository customerWriteRepository;
  private final AuthorizationService authorizationService;

  public CustomerUpdateService(
      CustomerReadRepository customerReadRepository,
      CustomerWriteRepository customerWriteRepository,
      AuthorizationService authorizationService) {
    this.customerReadRepository = customerReadRepository;
    this.customerWriteRepository = customerWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Customer updateCustomer(Integer id, String name, String address) {
    authorizationService.validatePermission(PermissionName.GESTION_VENTAS);

    Customer existingCustomer =
        customerReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Customer doesn't exist"));

    String updatedName = name != null ? name : existingCustomer.getName();

    validateName(updatedName);

    Customer updatedCustomer =
        new CustomerBuilder()
            .setId(existingCustomer.getId())
            .setName(updatedName)
            .setAddress(address != null ? address : existingCustomer.getAddress())
            .build();

    return customerWriteRepository.save(updatedCustomer);
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Customer name is required");
    }
  }
}