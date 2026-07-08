package com.unerp.service.sale;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.repository.sale.SaleRepository;

@Service
public class SaleGetService {

  private final SaleRepository saleRepository;

  public SaleGetService(SaleRepository saleRepository) {
    this.saleRepository = saleRepository;
  }

  public List<Sale> getAllSales() {
    return saleRepository.findAll();
  }

  public Sale getSaleById(Integer id) {
    return saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
  }

  public List<Sale> getSalesByStatus(String status) {
    SaleStatus saleStatus = SaleStatus.valueOf(status);
    return saleRepository.findByStatus(saleStatus);
  }
}

