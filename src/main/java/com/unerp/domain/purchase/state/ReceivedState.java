package com.unerp.domain.purchase.state;

public class ReceivedState extends PurchaseState {

  @Override
  public String getName() {
    return "Recibido";
  }

  @Override
  public PurchaseState pending() {
    return new PendingState();
  }

  @Override
  public PurchaseState received() {
    throw new IllegalStateException("La compra ya fue recibida");
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
