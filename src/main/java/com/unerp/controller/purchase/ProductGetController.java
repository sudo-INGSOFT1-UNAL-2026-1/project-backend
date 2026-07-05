package com.unerp.controller.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.product.Product;
import com.unerp.dto.product.ProductMapper;
import com.unerp.dto.product.ProductResponse;
import com.unerp.dto.product.ProductSearchRequest;
import com.unerp.service.product.ProductGetService;

@RestController
@RequestMapping("/product")
public class ProductGetController {

  private final ProductGetService productGetService;

  public ProductGetController(ProductGetService productGetService) {
    this.productGetService = productGetService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllProducts() {
    try {

      List<Product> products = productGetService.getAllProducts();

      List<ProductResponse> responseBody = new ArrayList<>();

      for (Product product : products) {
        responseBody.add(ProductMapper.toResponse(product));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> getProductById(@PathVariable Integer id) {
    try {

      Product product = productGetService.getProductById(id);

      return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toResponse(product));

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/search")
  public ResponseEntity<?> getProductsByParameters(
      ProductSearchRequest request) {
    try {

      List<Product> products =
          productGetService.getProductsByParameters(
              request.name(),
              request.description(),
              request.stock(),
              request.price(),
              request.batch(),
              request.expirationDate(),
              request.supplierId()
          );

      List<ProductResponse> responseBody = new ArrayList<>();
      for (Product product : products) {
        responseBody.add(ProductMapper.toResponse(product));
      }

      return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    } catch (IllegalStateException e) {

      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());

    } catch (SecurityException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
