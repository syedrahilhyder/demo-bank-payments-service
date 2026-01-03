package com.demo.bank.payments.app;

import com.demo.bank.payments.api.dto.*;
import com.demo.bank.payments.pipeline.PaymentPipeline;
import com.demo.bank.payments.usecase.PaymentUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentsFacadeImpl implements PaymentsFacade {

  private final PaymentPipeline pipeline;
  private final PaymentUseCase useCase;

  public PaymentsFacadeImpl(PaymentPipeline pipeline, PaymentUseCase useCase) {
    this.pipeline = pipeline;
    this.useCase = useCase;
  }

  @Override
  public UUID initiateLocal(LocalPaymentRequest req) {
    return pipeline.run("LOCAL", req.reference(), () -> useCase.initiateLocal(req));
  }

  @Override
  public UUID initiateInternational(InternationalPaymentRequest req) {
    return pipeline.run("INTERNATIONAL", req.reference(), () -> useCase.initiateInternational(req));
  }

  @Override
  public UUID initiateUtility(UtilityPaymentRequest req) {
    return pipeline.run("UTILITY", req.reference(), () -> useCase.initiateUtility(req));
  }

  @Override
  public void forceFinalize(UUID paymentId) {
    useCase.finalizePayment(paymentId, "FINALIZED", "manual");
  }
}
