package com.unerp.controller.sale;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unerp.domain.sale.Sale;
import com.unerp.dto.sale.SaleResponse;
import com.unerp.service.sale.SaleGetService;

@RestController
@RequestMapping("/sales")
public class SaleGetController {

  private final SaleGetService saleGetService;

  public SaleGetController(SaleGetService saleGetService) {
    this.saleGetService = saleGetService;
  }

  @GetMapping
  public ResponseEntity<List<SaleResponse>> getAllSales() {
    List<Sale> list = saleGetService.getAllSales();
    return ResponseEntity.ok(list.stream().map(SaleResponse::fromDomain).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SaleResponse> getSaleById(@PathVariable Integer id) {
    Sale sale = saleGetService.getSaleById(id);
    return ResponseEntity.ok(SaleResponse.fromDomain(sale));
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<SaleResponse>> getSalesByStatus(@PathVariable String status) {
    List<Sale> list = saleGetService.getSalesByStatus(status);
    return ResponseEntity.ok(list.stream().map(SaleResponse::fromDomain).toList());
  }
}

