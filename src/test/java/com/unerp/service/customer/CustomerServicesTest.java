package com.unerp.service.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.permission.PermissionName;
import com.unerp.repository.customer.CustomerReadRepository;
import com.unerp.repository.customer.CustomerWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServicesTest {

  @Mock private CustomerReadRepository customerReadRepository;
  @Mock private CustomerWriteRepository customerWriteRepository;
  @Mock private AuthorizationService authorizationService;

  private CustomerCreateService customerCreateService;
  private CustomerGetService customerGetService;
  private CustomerUpdateService customerUpdateService;
  private CustomerDeleteService customerDeleteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    customerCreateService =
        new CustomerCreateService(customerWriteRepository, authorizationService);
    customerGetService = new CustomerGetService(customerReadRepository, authorizationService);
    customerUpdateService =
        new CustomerUpdateService(
            customerReadRepository, customerWriteRepository, authorizationService);
    customerDeleteService =
        new CustomerDeleteService(
            customerReadRepository, customerWriteRepository, authorizationService);
  }

  @Test
  public void shouldCreateCustomerSuccessfully() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerWriteRepository.save(any(Customer.class)))
        .thenReturn(new Customer(1, "Main Customer", "Main Street 123"));

    Customer result = customerCreateService.createCustomer("Main Customer", "Main Street 123");

    assertEquals(1, result.getId());
    assertEquals("Main Customer", result.getName());
    assertEquals("Main Street 123", result.getAddress());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository).save(any(Customer.class));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerNameIsBlank() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> customerCreateService.createCustomer("   ", "Some Address"));

    assertEquals("Customer name is required", exception.getMessage());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository, never()).save(any(Customer.class));
  }

  @Test
  public void shouldGetAllCustomersSuccessfully() {
    Customer firstCustomer = new Customer(1, "Customer One", "Address One");
    Customer secondCustomer = new Customer(2, "Customer Two", "Address Two");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findAll()).thenReturn(List.of(firstCustomer, secondCustomer));

    List<Customer> result = customerGetService.getAllCustomers();

    assertEquals(2, result.size());
    assertEquals("Customer One", result.get(0).getName());
    assertEquals("Customer Two", result.get(1).getName());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerReadRepository).findAll();
  }

  @Test
  public void shouldGetCustomerByIdSuccessfully() {
    Customer customer = new Customer(1, "Customer One", "Address One");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(1)).thenReturn(Optional.of(customer));

    Customer result = customerGetService.getCustomerById(1);

    assertEquals(1, result.getId());
    assertEquals("Customer One", result.getName());
    assertEquals("Address One", result.getAddress());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerReadRepository).findById(1);
  }

  @Test
  public void shouldUpdateCustomerSuccessfully() {
    Customer existingCustomer = new Customer(1, "Old Customer", "Old Address");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
    when(customerWriteRepository.save(any(Customer.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Customer result = customerUpdateService.updateCustomer(1, "New Customer", "New Address");

    assertEquals(1, result.getId());
    assertEquals("New Customer", result.getName());
    assertEquals("New Address", result.getAddress());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository).save(any(Customer.class));
  }

  @Test
  public void shouldKeepExistingCustomerValuesWhenUpdateFieldsAreNull() {
    Customer existingCustomer = new Customer(1, "Current Customer", "Current Address");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(1)).thenReturn(Optional.of(existingCustomer));
    when(customerWriteRepository.save(any(Customer.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Customer result = customerUpdateService.updateCustomer(1, null, null);

    assertEquals(1, result.getId());
    assertEquals("Current Customer", result.getName());
    assertEquals("Current Address", result.getAddress());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository).save(any(Customer.class));
  }

  @Test
  public void shouldThrowExceptionWhenCustomerDoesNotExistOnUpdate() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(99)).thenReturn(Optional.empty());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> customerUpdateService.updateCustomer(99, "New Customer", null));

    assertEquals("Customer doesn't exist", exception.getMessage());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository, never()).save(any(Customer.class));
  }

  @Test
  public void shouldDeleteCustomerSuccessfully() {
    Customer customer = new Customer(1, "Customer One", "Address One");

    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(1)).thenReturn(Optional.of(customer));

    customerDeleteService.deleteCustomer(1);

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository).delete(customer);
  }

  @Test
  public void shouldThrowExceptionWhenCustomerDoesNotExistOnDelete() {
    doNothing().when(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    when(customerReadRepository.findById(99)).thenReturn(Optional.empty());

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> customerDeleteService.deleteCustomer(99));

    assertEquals("Customer doesn't exist", exception.getMessage());

    verify(authorizationService).validatePermission(PermissionName.GESTION_VENTAS);
    verify(customerWriteRepository, never()).delete(any(Customer.class));
  }
}