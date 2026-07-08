package com.unerp.domain.sale;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SaleStatusConverter implements AttributeConverter<SaleStatus, String> {

  @Override
  public String convertToDatabaseColumn(SaleStatus attribute) {
    if (attribute == null) return null;
    return switch (attribute) {
      case entregado -> "entregado";
      case por_enviar -> "por enviar";
      case en_camino -> "en camino";
      case cancelado -> "cancelado";
    };
  }

  @Override
  public SaleStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    String normalized = dbData.trim().toLowerCase();
    return switch (normalized) {
      case "entregado" -> SaleStatus.entregado;
      case "por enviar", "por_enviar" -> SaleStatus.por_enviar;
      case "en camino", "en_camino" -> SaleStatus.en_camino;
      case "cancelado" -> SaleStatus.cancelado;
      default -> throw new IllegalArgumentException("Unknown sale status: " + dbData);
    };
  }
}

