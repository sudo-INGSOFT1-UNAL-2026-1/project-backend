package com.unerp.controller.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.service.sale.SaleDeleteService;

@RestController
@RequestMapping("/sales")
public class SaleDeleteController {

  private final SaleDeleteService saleDeleteService;

  public SaleDeleteController(SaleDeleteService saleDeleteService) {
    this.saleDeleteService = saleDeleteService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSale(@PathVariable Integer id) {
    saleDeleteService.deleteSale(id);
    return ResponseEntity.ok().build();
  }
}

