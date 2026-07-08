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
}
