package com.unerp.service.supplier;

import com.unerp.domain.supplier.Supplier;
import com.unerp.domain.supplier.SupplierBuilder;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.supplier.SupplierWriteRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierCreateService {

  private final SupplierReadRepository supplierReadRepository;
  private final SupplierWriteRepository supplierWriteRepository;

  public SupplierCreateService(
      SupplierReadRepository supplierReadRepository,
      SupplierWriteRepository supplierWriteRepository) {
    this.supplierReadRepository = supplierReadRepository;
    this.supplierWriteRepository = supplierWriteRepository;
  }

  public Supplier createSupplier(String name, String phone, String email) {

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
