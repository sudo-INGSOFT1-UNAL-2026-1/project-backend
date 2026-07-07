package com.unerp.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.product.Product;
import com.unerp.dto.product.ProductMapper;
import com.unerp.dto.product.ProductSearchRequest;
import com.unerp.service.product.ProductUpdateService;

@RestController
@RequestMapping("/product")
public class ProductUpdateController {

  private final ProductUpdateService productUpdateService;

  public ProductUpdateController(ProductUpdateService productUpdateService) {
    this.productUpdateService = productUpdateService;
  }

  @PutMapping("/update/{productId}")
  public ResponseEntity<?> updateProduct(
      @PathVariable Integer productId,
      @RequestBody ProductSearchRequest request
  ) {
    try {

      Product product =
          productUpdateService.updateProduct(
              productId,
              request.name(),
              request.description(),
              request.stock(),
              request.price(),
              request.batch(),
              request.expirationDate(),
              request.supplierId()
          );

      return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toResponse(product));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
