package com.unerp.controller.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.sale.Sale;
import com.unerp.dto.sale.SaleUpdateRequest;
import com.unerp.dto.sale.SaleResponse;
import com.unerp.service.sale.SaleUpdateService;

@RestController
@RequestMapping("/sales")
public class SaleUpdateController {

  private final SaleUpdateService saleUpdateService;

  public SaleUpdateController(SaleUpdateService saleUpdateService) {
    this.saleUpdateService = saleUpdateService;
  }

  @PutMapping("/{id}")
  public ResponseEntity<SaleResponse> updateSale(@PathVariable Integer id, @RequestBody SaleUpdateRequest request) {
    Sale updated = saleUpdateService.updateSale(id, request);
    return ResponseEntity.ok(SaleResponse.fromDomain(updated));
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<SaleResponse> updateStatus(@PathVariable Integer id, @RequestParam String status) {
    Sale updated = saleUpdateService.updateStatus(id, status);
    return ResponseEntity.ok(SaleResponse.fromDomain(updated));
  }
}

