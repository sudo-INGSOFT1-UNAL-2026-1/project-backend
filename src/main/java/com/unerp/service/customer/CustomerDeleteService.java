package com.unerp.service.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.customer.CustomerReadRepository;
import com.unerp.repository.customer.CustomerWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class CustomerDeleteService {

  private final CustomerReadRepository customerReadRepository;
  private final CustomerWriteRepository customerWriteRepository;
  private final AuthorizationService authorizationService;

  public CustomerDeleteService(
      CustomerReadRepository customerReadRepository,
      CustomerWriteRepository customerWriteRepository,
      AuthorizationService authorizationService) {
    this.customerReadRepository = customerReadRepository;
    this.customerWriteRepository = customerWriteRepository;
    this.authorizationService = authorizationService;
  }

  public void deleteCustomer(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_VENTAS);

    Customer customer =
        customerReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Customer doesn't exist"));

    customerWriteRepository.delete(customer);
  }
}