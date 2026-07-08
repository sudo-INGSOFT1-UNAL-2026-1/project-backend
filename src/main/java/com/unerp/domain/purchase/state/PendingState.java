package com.unerp.domain.purchase.state;

public class PendingState extends PurchaseState {

  @Override
  public String getName() {
    return "Pendiente";
  }

  @Override
  public PurchaseState pending() {
    throw new IllegalStateException("La compra ya esta pendiente");
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
    return new CanceledState();
  }
}
