package com.unerp.service.product;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unerp.domain.product.Product;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.repository.supplier.SupplierReadRepository;
import com.unerp.service.auth.AuthorizationService;

@ExtendWith(MockitoExtension.class)
public class ProductCreateServiceTest {

  @Mock private ProductReadRepository productReadRepository;
  @Mock private SupplierReadRepository supplierReadRepository;
  @Mock private ProductWriteRepository productWriteRepository;
  @Mock private AuthorizationService authorizationService;

  @InjectMocks private ProductCreateService productCreateService;

  @Test
  public void shouldCreateProduct() {

    doNothing().when(authorizationService).validatePermission(any());
    
    when(supplierReadRepository.existsById(any())).thenReturn(true);        

    when(productWriteRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
    Product product = productCreateService.createProduct("Papas", "Deliciosas papas", 10, new BigDecimal("2500"), "PapasBatch", null, 1);

    assertNotNull(product);
    assertEquals("Papas", product.getName());
    assertEquals("Deliciosas papas", product.getDescription());
    assertEquals(10, product.getStock());
    assertEquals(new BigDecimal("2500"), product.getPrice());
    assertEquals("PapasBatch", product.getBatch());
    assertEquals(1, product.getSupplierId());
    verify(productWriteRepository).save(any(Product.class));
  }

  @Test
  public void shouldThrowExceptionWhenProductAlreadyExists() {

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.existsByName("Papas")).thenReturn(true);
    when(productReadRepository.existsByBatch("PapasBatch")).thenReturn(true);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            productCreateService.createProduct("Papas", "Deliciosas papas", 10, new BigDecimal("2500"), "PapasBatch", null, 1));
  }

  @Test
  public void shouldThrowExceptionWhenProductNameAlreadyExists() {

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.existsByName("Papas")).thenReturn(true);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            productCreateService.createProduct("Papas", "Deliciosas papas", 10, new BigDecimal("2500"), "PapasBatch", null, 1));
  }

  @Test
  public void shouldThrowExceptionWhenProductBatchAlreadyExists() {

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.existsByName("Papas")).thenReturn(true);
    when(productReadRepository.existsByBatch("PapasBatch")).thenReturn(true);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            productCreateService.createProduct("Papas", "Deliciosas papas", 10, new BigDecimal("2500"), "PapasBatch", null, 1));
  }

  @Test
  public void shouldThrowExceptionWhenSupplierDoesNotExist() {

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.existsByName("Papas")).thenReturn(true);
    when(productReadRepository.existsByBatch("PapasBatch")).thenReturn(true);
    lenient().when(supplierReadRepository.existsById(1)).thenReturn(false);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            productCreateService.createProduct("Papas", "Deliciosas papas", 10, new BigDecimal("2500"), "PapasBatch", null, 1));
  }
}
