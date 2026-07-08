package com.unerp.service.sale;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.dto.sale.SaleUpdateRequest;
import com.unerp.repository.sale.SaleRepository;

@Service
public class SaleUpdateService {

  private final SaleRepository saleRepository;

  public SaleUpdateService(SaleRepository saleRepository) {
    this.saleRepository = saleRepository;
  }

  public Sale updateSale(Integer id, SaleUpdateRequest request) {
    Sale existing = saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    if (existing.getStatus() == SaleStatus.entregado || existing.getStatus() == SaleStatus.cancelado) {
      throw new IllegalArgumentException("Cannot modify delivered or canceled sale");
    }
    if (request.deliveryDate() != null) {
      existing.setDeliveryDate(LocalDate.parse(request.deliveryDate()));
    }
    if (request.description() != null) {
      existing.setDescription(request.description());
    }
    if (request.status() != null) {
      existing.setStatus(SaleStatus.valueOf(request.status()));
    }
    return saleRepository.save(existing);
  }

  public Sale updateStatus(Integer id, String status) {
    Sale sale = saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    SaleStatus newStatus = SaleStatus.valueOf(status);
    sale.setStatus(newStatus);
    return saleRepository.save(sale);
  }
}

