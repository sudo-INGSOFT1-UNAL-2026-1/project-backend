package com.unerp.domain.purchase.state;

public class PaidState extends PurchaseState {

  @Override
  public String getName() {
    return "Pagado";
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
    throw new IllegalStateException("La compra ya esta pagada");
  }

  @Override
  public PurchaseState canceled() {
    return new CanceledState();
  }
}
