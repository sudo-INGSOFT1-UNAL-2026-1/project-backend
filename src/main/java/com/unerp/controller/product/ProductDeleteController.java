package com.unerp.controller.product;

import com.unerp.service.product.ProductDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductDeleteController {

  private final ProductDeleteService productDeleteService;

  public ProductDeleteController(ProductDeleteService productDeleteService) {
    this.productDeleteService = productDeleteService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProductById( @PathVariable Integer id) {
    try {
      productDeleteService.deleteProductById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/batch/{batch}")
  public ResponseEntity<?> deleteProductByBatch(@PathVariable String batch) {
    try {
      productDeleteService.deleteProductByBatch(batch);
      return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/supplier/{supplierId}")
  public ResponseEntity<?> deleteProductBySupplierId(@PathVariable Integer supplierId) {
    try {
      productDeleteService.deleteProductBySupplierId(supplierId);
      return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (SecurityException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
