package com.unerp.service.supplier;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.supplier.Supplier;
import com.unerp.domain.supplier.SupplierBuilder;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.supplier.SupplierWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SupplierUpdateService {

  private final SupplierReadRepository supplierReadRepository;
  private final SupplierWriteRepository supplierWriteRepository;
  private final AuthorizationService authorizationService;

  public SupplierUpdateService(
      SupplierReadRepository supplierReadRepository,
      SupplierWriteRepository supplierWriteRepository,
      AuthorizationService authorizationService) {
    this.supplierReadRepository = supplierReadRepository;
    this.supplierWriteRepository = supplierWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Supplier updateSupplier(Integer id, String name, String phone, String email) {
    authorizationService.validatePermission(PermissionName.GESTION_COMPRAS);

    Supplier existingSupplier =
        supplierReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Supplier doesn't exist"));

    String updatedEmail = email != null ? email : existingSupplier.getEmail();

    if (email != null) {
      validateEmailFormat(email);
      validateAvailableEmail(existingSupplier, email);
    }

    Supplier updatedSupplier =
        new SupplierBuilder()
            .setId(existingSupplier.getId())
            .setName(name != null ? name : existingSupplier.getName())
            .setPhone(phone != null ? phone : existingSupplier.getPhone())
            .setEmail(updatedEmail)
            .build();

    return supplierWriteRepository.save(updatedSupplier);
  }

  private void validateAvailableEmail(Supplier existingSupplier, String email) {
    if (!email.equals(existingSupplier.getEmail()) && supplierReadRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already exists");
    }
  }

  private void validateEmailFormat(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    if (!email.matches(emailRegex)) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }
}