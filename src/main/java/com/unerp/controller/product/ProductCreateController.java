package com.unerp.controller.product;

import com.unerp.domain.product.Product;
import com.unerp.dto.product.ProductCreateRequest;
import com.unerp.dto.product.ProductMapper;
import com.unerp.service.product.ProductCreateService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductCreateController {

  private final ProductCreateService productCreateService;

  public ProductCreateController(ProductCreateService productCreateService) {
    this.productCreateService = productCreateService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createProduct(
    @Valid @RequestBody ProductCreateRequest request
  ) {

    try {
      Product product =
          productCreateService.createProduct(
              request.name(),
              request.description(),
              request.stock(),
              request.price(),
              request.batch(),
              request.expirationDate(),
              request.supplierId()
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toResponse(product));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
