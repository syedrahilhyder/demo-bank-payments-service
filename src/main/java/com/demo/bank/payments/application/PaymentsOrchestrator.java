package com.demo.bank.payments.application;

import com.demo.bank.events.*;
import com.demo.bank.payments.domain.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PaymentsOrchestrator {

  private final PaymentRepository repo;
  private final KafkaTemplate<String, Object> kafka;
  private final RestTemplate rest = new RestTemplate();

  public PaymentsOrchestrator(PaymentRepository repo, KafkaTemplate<String, Object> kafka) {
    this.repo = repo;
    this.kafka = kafka;
  }

  public UUID initiate(String customerId, String from, String to, long amount, String currency) {
    Payment payment = new Payment(
        UUID.randomUUID(), customerId, from, to, amount, currency, "INITIATED"
    );
    repo.save(payment);

    // Call account-service
    rest.postForObject(
        "http://localhost:8081/accounts/" + from + "/debit?amountMinor=" + amount,
        null,
        Void.class
    );

    // Publish event
    PaymentInitiatedEvent event = new PaymentInitiatedEvent(
        payment.getId().toString(), "LOCAL_TRANSFER", customerId, amount, currency, "REF-1"
    );
    kafka.send(EventTopics.PAYMENTS_EVENTS, payment.getId().toString(), event);

    return payment.getId();
  }
}
