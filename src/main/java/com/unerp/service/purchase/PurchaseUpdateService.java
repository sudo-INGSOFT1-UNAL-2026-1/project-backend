package com.unerp.service.purchase;

import com.unerp.domain.permission.PermissionName;
import com.unerp.domain.product.Product;
import com.unerp.domain.product.ProductBuilder;
import com.unerp.repository.product.ProductReadRepository;
import com.unerp.repository.product.ProductWriteRepository;
import com.unerp.service.auth.AuthorizationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseUpdateService {

  private final ProductReadRepository productReadRepository;
  private final ProductWriteRepository productWriteRepository;
  private final AuthorizationService authorizationService;

  public ProductUpdateService(
      ProductReadRepository productReadRepository,
      ProductWriteRepository productWriteRepository,
      AuthorizationService authorizationService) {
    this.productReadRepository = productReadRepository;
    this.productWriteRepository = productWriteRepository;
    this.authorizationService = authorizationService;
  }

  public Product updateProduct(
      Integer id,
      String name,
      String description,
      Integer stock,
      BigDecimal price,
      String batch,
      LocalDate expirationDate,
      Integer supplierId) {

    authorizationService.validatePermission(PermissionName.GESTION_INVENTARIO);
    Product existingProduct =
        productReadRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product doesn't exist"));

    String existingName = existingProduct.getName();
    String existingDescription = existingProduct.getDescription();
    Integer existingStock = existingProduct.getStock();
    BigDecimal existingPrice = existingProduct.getPrice();
    String existingBatch = existingProduct.getBatch();
    LocalDate existingExpirationDate = existingProduct.getExpirationDate();
    Integer existingSupplierId = existingProduct.getSupplierId();

    Product updatedProduct =
        new ProductBuilder()
            .setId(id)
            .setName(name != null ? name : existingName)
            .setDescription(description != null ? description : existingDescription)
            .setStock(stock != null ? stock : existingStock)
            .setPrice(price != null ? price : existingPrice)
            .setBatch(batch != null ? batch : existingBatch)
            .setExpirationDate(expirationDate != null ? expirationDate : existingExpirationDate)
            .setSupplierId(supplierId != null ? supplierId : existingSupplierId)
            .build();

    return productWriteRepository.save(updatedProduct);
  }
}