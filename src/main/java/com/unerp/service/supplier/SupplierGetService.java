package com.unerp.service.supplier;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.supplier.Supplier;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SupplierGetService {

  private final SupplierReadRepository supplierReadRepository;
  private final AuthorizationService authorizationService;

  public SupplierGetService(
      SupplierReadRepository supplierReadRepository,
      AuthorizationService authorizationService) {
    this.supplierReadRepository = supplierReadRepository;
    this.authorizationService = authorizationService;
  }

  public List<Supplier> getAllSuppliers() {
    authorizationService.validatePermission(PermissionName.GESTION_COMPRAS);
    return supplierReadRepository.findAll();
  }

  public Supplier getSupplierById(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_COMPRAS);

    return supplierReadRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Supplier doesn't exist"));
  }
}