package com.unerp.domain.purchase.state;

public abstract class PurchaseState {

  public abstract String getName();

  public abstract PurchaseState pending();

  public abstract PurchaseState received();

  public abstract PurchaseState paid();

  public abstract PurchaseState canceled();

  public PurchaseState deactivate() {
    return this;
  }

  public static PurchaseState fromName(String name) {

    return switch (name.toUpperCase()) {
      case "PENDIENTE" -> new PendingState();
      case "PAGADO" -> new PaidState();
      case "RECIBIDO" -> new ReceivedState();
      case "CANCELADO" -> new CanceledState();
      default -> throw new IllegalArgumentException("Unknown purchase state: " + name);
    };
  }
}
