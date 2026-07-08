package com.unerp.service.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unerp.domain.customer.Customer;
import com.unerp.domain.sale.Sale;
import com.unerp.domain.sale.SaleProduct;
import com.unerp.domain.sale.SaleStatus;
import com.unerp.dto.sale.SaleCreateRequest;
import com.unerp.repository.customer.CustomerRepository;
import com.unerp.repository.sale.SaleRepository;
import com.unerp.service.ProductService;

@Service
public class SaleCreateService {

  private final SaleRepository saleRepository;
  private final CustomerRepository customerRepository;
  private final ProductService productService;

  public SaleCreateService(SaleRepository saleRepository, CustomerRepository customerRepository, ProductService productService) {
    this.saleRepository = saleRepository;
    this.customerRepository = customerRepository;
    this.productService = productService;
  }

  public Sale createQuotation(SaleCreateRequest request) {
    Integer customerId = request.customerId();
    if (customerId == null && request.customerName() != null) {
      Customer newCustomer = new Customer(request.customerName(), request.customerAddress());
      Customer saved = customerRepository.save(newCustomer);
      customerId = saved.getId();
    }
    if (customerId == null) {
      throw new IllegalArgumentException("customerId or customerName is required");
    }

    Sale sale = new Sale();
    sale.setCustomerId(customerId);
    sale.setUserId(request.userId());
    sale.setDescription(request.description());
    sale.setDeliveryDate(LocalDate.parse(request.deliveryDate()));
    sale.setStatus(SaleStatus.por_enviar);
    sale.setTotalCost(BigDecimal.ZERO);

    Sale savedSale = saleRepository.save(sale);

    BigDecimal total = BigDecimal.ZERO;
    List<SaleProduct> products = new ArrayList<>();

    for (var prod : request.products()) {
      var product = productService.getProductById(prod.productId());
      if (product.getStock() < prod.quantity()) {
        throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
      }
      SaleProduct sp = new SaleProduct();
      sp.setSale(savedSale);
      sp.setProductId(prod.productId());
      sp.setQuantity(prod.quantity());
      sp.setUnitPrice(prod.unitPrice());
      sp.calculateSubtotal();
      products.add(sp);
      productService.updateStock(prod.productId(), -prod.quantity());
      total = total.add(sp.getSubtotal());
    }

    savedSale.setProducts(products);
    savedSale.setTotalCost(total);
    return saleRepository.save(savedSale);
  }
}

