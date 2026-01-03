package com.demo.bank.payments.usecase.impl;

import com.demo.bank.events.EventTopics;
import com.demo.bank.events.PaymentFinalizedEvent;
import com.demo.bank.events.PaymentInitiatedEvent;
import com.demo.bank.payments.api.dto.*;
import com.demo.bank.payments.connectors.LimitsConnector;
import com.demo.bank.payments.domain.Payment;
import com.demo.bank.payments.domain.PaymentRepository;
import com.demo.bank.payments.strategy.PaymentStrategy;
import com.demo.bank.payments.strategy.StrategyFactory;
import com.demo.bank.payments.usecase.PaymentUseCase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentUseCaseImpl implements PaymentUseCase {

  private final PaymentRepository repo;
  private final PaymentValidators validators;
  private final LimitsConnector limits;
  private final StrategyFactory strategies;
  private final KafkaTemplate<String, Object> kafka;

  public PaymentUseCaseImpl(PaymentRepository repo, PaymentValidators validators, LimitsConnector limits, StrategyFactory strategies, KafkaTemplate<String, Object> kafka) {
    this.repo = repo;
    this.validators = validators;
    this.limits = limits;
    this.strategies = strategies;
    this.kafka = kafka;
  }

  @Override
  public UUID initiateLocal(LocalPaymentRequest req) {
    validators.validateCommon(req.customerId(), req.amountMinor(), req.currency());
    validators.validateAccounts(req.fromAccount(), req.toAccount());
    return initiate("LOCAL", req.customerId(), req.fromAccount(), req.toAccount(), req.amountMinor(), req.currency(), req.reference());
  }

  @Override
  public UUID initiateInternational(InternationalPaymentRequest req) {
    validators.validateCommon(req.customerId(), req.amountMinor(), req.currency());
    validators.validateInternational(req.beneficiaryIban(), req.swiftBic());
    return initiate("INTERNATIONAL", req.customerId(), req.fromAccount(), null, req.amountMinor(), req.currency(), req.reference());
  }

  @Override
  public UUID initiateUtility(UtilityPaymentRequest req) {
    validators.validateCommon(req.customerId(), req.amountMinor(), req.currency());
    validators.validateUtility(req.billerCode(), req.billRef());
    return initiate("UTILITY", req.customerId(), req.fromAccount(), null, req.amountMinor(), req.currency(), req.reference());
  }

  private UUID initiate(String kind, String customerId, String from, String to, long amount, String currency, String reference) {
    if (!limits.allow(customerId, amount)) throw new IllegalStateException("Limits exceeded");

    Payment p = new Payment(UUID.randomUUID(), kind, customerId, from, to, amount, currency, reference);
    repo.save(p);

    PaymentStrategy s = strategies.resolve(kind);
    s.preProcess(p);
    repo.save(p);

    kafka.send(EventTopics.PAYMENTS_EVENTS, p.getId().toString(), new PaymentInitiatedEvent(
        p.getId().toString(), kind, customerId, amount, currency, reference
    ));

    return p.getId();
  }

  @Override
  public void finalizePayment(UUID paymentId, String finalStatus, String reason) {
    Payment p = repo.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("payment not found"));
    if ("FINALIZED".equals(p.getStatus())) return;

    if ("FINALIZED".equals(finalStatus)) p.markFinalized();
    else p.markFailed(reason);

    repo.save(p);

    kafka.send(EventTopics.PAYMENTS_EVENTS, paymentId.toString(), new PaymentFinalizedEvent(paymentId.toString(), p.getStatus(), reason));
  }
}
