package com.unerp.service.supplier;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.supplier.Supplier;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.supplier.SupplierWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class SupplierDeleteService {

  private final SupplierReadRepository supplierReadRepository;
  private final SupplierWriteRepository supplierWriteRepository;
  private final AuthorizationService authorizationService;

  public SupplierDeleteService(
      SupplierReadRepository supplierReadRepository,
      SupplierWriteRepository supplierWriteRepository,
      AuthorizationService authorizationService) {
    this.supplierReadRepository = supplierReadRepository;
    this.supplierWriteRepository = supplierWriteRepository;
    this.authorizationService = authorizationService;
  }

  public void deleteSupplier(Integer id) {
    authorizationService.validatePermission(PermissionName.GESTION_COMPRAS);

    Supplier supplier =
        supplierReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Supplier doesn't exist"));

    supplierWriteRepository.delete(supplier);
  }
}
