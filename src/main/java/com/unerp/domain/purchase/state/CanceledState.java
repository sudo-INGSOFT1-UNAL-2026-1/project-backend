package com.unerp.domain.purchase.state;

public class CanceledState extends PurchaseState {

  @Override
  public String getName() {
    return "Cancelado";
  }

  @Override
  public PurchaseState pending() {
    return new PendingState();
  }

  @Override
  public PurchaseState received() {
    return new ReceivedState();
  }

  @Override
  public PurchaseState paid() {
    return new PaidState();
  }

  @Override
  public PurchaseState canceled() {
    throw new IllegalStateException("La compra ya esta cancelada");
  }
}
