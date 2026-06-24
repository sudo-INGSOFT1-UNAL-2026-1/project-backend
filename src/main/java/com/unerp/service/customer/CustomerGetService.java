package com.unerp.service.customer;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.customer.CustomerReadRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerGetService {

  private final CustomerReadRepository customerReadRepository;
  private final AuthorizationService authorizationService;

  public CustomerGetService(
      CustomerReadRepository customerReadRepository,
      AuthorizationService authorizationService) {
    this.customerReadRepository = customerReadRepository;
    this.authorizationService = authorizationService;
  }

  public List<Customer> getAllCustomers() {
    authorizationService.validatePermission(PermissionName.GESTION_VENTAS);
    return customerReadRepository.findAll();
  }

  public Customer getCustomerById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_VENTAS);

    return customerReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Customer doesn't exist"));
  }
}