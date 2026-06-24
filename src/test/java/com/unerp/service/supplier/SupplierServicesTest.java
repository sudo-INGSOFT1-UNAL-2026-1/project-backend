package com.unerp.service.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.supplier.Supplier;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.repository.supplier.SupplierWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SupplierServicesTest {

  @Mock private SupplierReadRepository supplierReadRepository;
  @Mock private SupplierWriteRepository supplierWriteRepository;
  @Mock private AuthorizationService authorizationService;

  private SupplierGetService supplierGetService;
  private SupplierUpdateService supplierUpdateService;
  private SupplierDeleteService supplierDeleteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    supplierGetService = new SupplierGetService(supplierReadRepository, authorizationService);
    supplierUpdateService =
        new SupplierUpdateService(
            supplierReadRepository, supplierWriteRepository, authorizationService);
    supplierDeleteService =
        new SupplierDeleteService(
            supplierReadRepository, supplierWriteRepository, authorizationService);
  }

  @Test
  public void shouldGetAllSuppliersSuccessfully() {
    Supplier firstSupplier = new Supplier(1, "Rice Supplier", "3001112233", "rice@supplier.com");
    Supplier secondSupplier = new Supplier(2, "Oil Supplier", "3004445566", "oil@supplier.com");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findAll()).thenReturn(List.of(firstSupplier, secondSupplier));

    List<Supplier> result = supplierGetService.getAllSuppliers();

    assertEquals(2, result.size());
    assertEquals("Rice Supplier", result.get(0).getName());
    assertEquals("Oil Supplier", result.get(1).getName());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierReadRepository).findAll();
  }

  @Test
  public void shouldGetSupplierByIdSuccessfully() {
    Supplier supplier = new Supplier(1, "Rice Supplier", "3001112233", "rice@supplier.com");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(1)).thenReturn(Optional.of(supplier));

    Supplier result = supplierGetService.getSupplierById(1);

    assertEquals(1, result.getId());
    assertEquals("Rice Supplier", result.getName());
    assertEquals("3001112233", result.getPhone());
    assertEquals("rice@supplier.com", result.getEmail());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierReadRepository).findById(1);
  }

  @Test
  public void shouldUpdateSupplierSuccessfully() {
    Supplier existingSupplier = new Supplier(1, "Old Supplier", "3000000000", "old@supplier.com");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(1)).thenReturn(Optional.of(existingSupplier));
    when(supplierReadRepository.existsByEmail("new@supplier.com")).thenReturn(false);
    when(supplierWriteRepository.save(any(Supplier.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Supplier result =
        supplierUpdateService.updateSupplier(1, "New Supplier", "3111111111", "new@supplier.com");

    assertEquals(1, result.getId());
    assertEquals("New Supplier", result.getName());
    assertEquals("3111111111", result.getPhone());
    assertEquals("new@supplier.com", result.getEmail());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierWriteRepository).save(any(Supplier.class));
  }

  @Test
  public void shouldKeepExistingSupplierValuesWhenUpdateFieldsAreNull() {
    Supplier existingSupplier =
        new Supplier(1, "Current Supplier", "3222222222", "current@supplier.com");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(1)).thenReturn(Optional.of(existingSupplier));
    when(supplierWriteRepository.save(any(Supplier.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Supplier result = supplierUpdateService.updateSupplier(1, null, null, null);

    assertEquals(1, result.getId());
    assertEquals("Current Supplier", result.getName());
    assertEquals("3222222222", result.getPhone());
    assertEquals("current@supplier.com", result.getEmail());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierWriteRepository).save(any(Supplier.class));
  }

  @Test
  public void shouldThrowExceptionWhenSupplierDoesNotExistOnUpdate() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(99)).thenReturn(Optional.empty());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> supplierUpdateService.updateSupplier(99, "New Supplier", null, null));

    assertEquals("Supplier doesn't exist", exception.getMessage());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierWriteRepository, never()).save(any(Supplier.class));
  }

  @Test
  public void shouldDeleteSupplierSuccessfully() {
    Supplier supplier = new Supplier(1, "Rice Supplier", "3001112233", "rice@supplier.com");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(1)).thenReturn(Optional.of(supplier));

    supplierDeleteService.deleteSupplier(1);

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierWriteRepository).delete(supplier);
  }

  @Test
  public void shouldThrowExceptionWhenSupplierDoesNotExistOnDelete() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    when(supplierReadRepository.findById(99)).thenReturn(Optional.empty());

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> supplierDeleteService.deleteSupplier(99));

    assertEquals("Supplier doesn't exist", exception.getMessage());

    verify(authorizationService).validatePermission(PermissionName.GESTION_COMPRAS);
    verify(supplierWriteRepository, never()).delete(any(Supplier.class));
  }
}