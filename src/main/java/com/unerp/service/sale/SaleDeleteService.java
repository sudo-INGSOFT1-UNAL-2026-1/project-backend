package com.unerp.service.sale;

import org.springframework.stereotype.Service;

import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleProduct;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.repository.sale.SaleRepository;
import com.unerp.service.ProductService;

@Service
public class SaleDeleteService {

  private final SaleRepository saleRepository;
  private final ProductService productService;

  public SaleDeleteService(SaleRepository saleRepository, ProductService productService) {
    this.saleRepository = saleRepository;
    this.productService = productService;
  }

  public void deleteSale(Integer id) {
    Sale sale = saleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    if (sale.getStatus() == SaleStatus.entregado) {
      throw new IllegalArgumentException("Cannot delete delivered sale");
    }
    for (SaleProduct sp : sale.getProducts()) {
      productService.updateStock(sp.getProductId(), sp.getQuantity());
    }
    saleRepository.delete(sale);
  }
}

