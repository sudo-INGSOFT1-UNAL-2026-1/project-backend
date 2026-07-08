package com.unerp.controller.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.sale.Sale;
import com.unerp.dto.sale.SaleCreateRequest;
import com.unerp.dto.sale.SaleResponse;
import com.unerp.service.sale.SaleCreateService;

@RestController
@RequestMapping("/sales")
public class SaleCreateController {

  private final SaleCreateService saleCreateService;

  public SaleCreateController(SaleCreateService saleCreateService) {
    this.saleCreateService = saleCreateService;
  }

  @PostMapping("/quote")
  public ResponseEntity<SaleResponse> createQuotation(@RequestBody SaleCreateRequest request) {
    Sale created = saleCreateService.createQuotation(request);
    return ResponseEntity.status(201).body(SaleResponse.fromDomain(created));
  }
}

