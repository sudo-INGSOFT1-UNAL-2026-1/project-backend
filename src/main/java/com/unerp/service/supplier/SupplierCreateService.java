package com.unerp.service.supplier;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.supplier.Supplier;
import com.unerp.domain.supplier.SupplierBuilder;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.supplier.SupplierWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SupplierCreateService {

  private final SupplierReadRepository supplierReadRepository;
  private final SupplierWriteRepository supplierWriteRepository;
  private final AuthorizationService authorizationService;

  public SupplierCreateService(
      SupplierReadRepository supplierReadRepository,
      SupplierWriteRepository supplierWriteRepository,
      AuthorizationService authorizationService) {
    this.supplierReadRepository = supplierReadRepository;
    this.supplierWriteRepository = supplierWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Supplier createSupplier(String name, String phone, String email) {

    authorizationService.validatePermission(PermissionName.GESTION_COMPRAS);
    validateAvailableEmail(email);
    validateEmailFormat(email);

    Supplier newSupplier =
        new SupplierBuilder().setName(name).setPhone(phone).setEmail(email).build();

    return supplierWriteRepository.save(newSupplier);
  }

  public void validateAvailableEmail(String email) {
    if (supplierReadRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }
  }

  public void validateEmailFormat(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    if (!email.matches(emailRegex)) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }
}
