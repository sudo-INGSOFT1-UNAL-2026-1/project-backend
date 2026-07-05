package com.unerp.domain.purchase.state;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class PurchaseStateTest {

  @Test
  void deactivateShouldKeepTheCurrentStateByDefault() {
    PurchaseState pendingState = new PendingState();
    PurchaseState receivedState = new ReceivedState();
    PurchaseState paidState = new PaidState();
    PurchaseState canceledState = new CanceledState();

    assertSame(pendingState, pendingState.deactivate());
    assertSame(receivedState, receivedState.deactivate());
    assertSame(paidState, paidState.deactivate());
    assertSame(canceledState, canceledState.deactivate());
  }
}
