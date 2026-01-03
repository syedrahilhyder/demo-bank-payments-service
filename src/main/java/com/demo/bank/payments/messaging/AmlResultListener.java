package com.demo.bank.payments.messaging;

import com.demo.bank.events.AmlScreeningResultEvent;
import com.demo.bank.payments.connectors.AccountConnector;
import com.demo.bank.payments.domain.Payment;
import com.demo.bank.payments.domain.PaymentRepository;
import com.demo.bank.payments.usecase.PaymentUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AmlResultListener {

  private final PaymentRepository repo;
  private final AccountConnector account;
  private final PaymentUseCase useCase;

  public AmlResultListener(PaymentRepository repo, AccountConnector account, PaymentUseCase useCase) {
    this.repo = repo;
    this.account = account;
    this.useCase = useCase;
  }

  @KafkaListener(topics = com.demo.bank.events.EventTopics.AML_EVENTS)
  public void onAml(AmlScreeningResultEvent e) {
    UUID paymentId = UUID.fromString(e.paymentId());
    Payment p = repo.findById(paymentId).orElse(null);
    if (p == null) return;

    if ("PASS".equalsIgnoreCase(e.decision())) {
      if (p.getFromAccount() != null) account.debit(p.getFromAccount(), p.getAmountMinor());
      useCase.finalizePayment(paymentId, "FINALIZED", "aml_pass");
    } else {
      useCase.finalizePayment(paymentId, "FAILED", "aml_hold");
    }
  }
}
