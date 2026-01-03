package com.demo.bank.payments.app;

import com.demo.bank.payments.api.dto.*;

import java.util.UUID;

public interface PaymentsFacade {
  UUID initiateLocal(LocalPaymentRequest req);
  UUID initiateInternational(InternationalPaymentRequest req);
  UUID initiateUtility(UtilityPaymentRequest req);
  void forceFinalize(UUID paymentId);
}
