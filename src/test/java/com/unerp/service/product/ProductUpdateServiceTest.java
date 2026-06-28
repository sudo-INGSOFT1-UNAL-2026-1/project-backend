package com.unerp.service.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.unerp.domain.product.Product;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductUpdateServiceTest {

  @Mock
  private ProductReadRepository productReadRepository;

  @Mock
  private ProductWriteRepository productWriteRepository;

  @Mock
  private AuthorizationService authorizationService;

  @InjectMocks
  private ProductUpdateService productUpdateService;

  @Test
  public void shouldUpdateProductSuccessfully() {
    Product existingProduct =
        new Product(
            1,
            "Arroz",
            "Arroz blanco",
            20,
            new BigDecimal("3500"),
            "Lote-A",
            LocalDate.of(2026, 12, 31),
            1);

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.findById(1)).thenReturn(Optional.of(existingProduct));
    when(productWriteRepository.save(any(Product.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Product result =
        productUpdateService.updateProduct(
            1,
            "Arroz Premium",
            "Arroz blanco premium",
            50,
            new BigDecimal("4200"),
            "Lote-B",
            LocalDate.of(2027, 1, 15),
            2);

    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("Arroz Premium", result.getName());
    assertEquals("Arroz blanco premium", result.getDescription());
    assertEquals(50, result.getStock());
    assertEquals(new BigDecimal("4200"), result.getPrice());
    assertEquals("Lote-B", result.getBatch());
    assertEquals(LocalDate.of(2027, 1, 15), result.getExpirationDate());
    assertEquals(2, result.getSupplierId());

    verify(authorizationService).validatePermission(any());
    verify(productWriteRepository).save(any(Product.class));
  }

  @Test
  public void shouldKeepExistingValuesWhenUpdateFieldsAreNull() {
    Product existingProduct =
        new Product(
            3,
            "Aceite",
            "Aceite vegetal",
            15,
            new BigDecimal("12000"),
            "Lote-C",
            LocalDate.of(2026, 8, 20),
            4);

    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.findById(3)).thenReturn(Optional.of(existingProduct));
    when(productWriteRepository.save(any(Product.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Product result =
        productUpdateService.updateProduct(
            3,
            null,
            null,
            null,
            null,
            null,
            null,
            null);

    assertNotNull(result);
    assertEquals(3, result.getId());
    assertEquals("Aceite", result.getName());
    assertEquals("Aceite vegetal", result.getDescription());git status
    assertEquals(15, result.getStock());
    assertEquals(new BigDecimal("12000"), result.getPrice());
    assertEquals("Lote-C", result.getBatch());
    assertEquals(LocalDate.of(2026, 8, 20), result.getExpirationDate());
    assertEquals(4, result.getSupplierId());

    verify(authorizationService).validatePermission(any());
    verify(productWriteRepository).save(any(Product.class));
  }

  @Test
  public void shouldThrowExceptionWhenProductDoesNotExist() {
    doNothing().when(authorizationService).validatePermission(any());
    when(productReadRepository.findById(99)).thenReturn(Optional.empty());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                productUpdateService.updateProduct(
                    99,
                    "Producto inexistente",
                    "No existe",
                    10,
                    new BigDecimal("5000"),
                    "Lote-X",
                    LocalDate.of(2026, 10, 10),
                    1));

    assertEquals("Product doesn't exist", exception.getMessage());

    verify(authorizationService).validatePermission(any());
    verify(productWriteRepository, never()).save(any(Product.class));
  }
}
