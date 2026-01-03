package com.demo.bank.payments.usecase;

import com.demo.bank.payments.api.dto.*;

import java.util.UUID;

public interface PaymentUseCase {
  UUID initiateLocal(LocalPaymentRequest req);
  UUID initiateInternational(InternationalPaymentRequest req);
  UUID initiateUtility(UtilityPaymentRequest req);

  void finalizePayment(UUID paymentId, String finalStatus, String reason);
}
