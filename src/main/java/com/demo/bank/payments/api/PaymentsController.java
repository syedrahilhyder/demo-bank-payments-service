package com.demo.bank.payments.api;

import com.demo.bank.payments.application.PaymentsOrchestrator;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

  private final PaymentsOrchestrator orchestrator;

  public PaymentsController(PaymentsOrchestrator orchestrator) {
    this.orchestrator = orchestrator;
  }

  @PostMapping("/local")
  public UUID localPayment(@RequestParam String customerId,
                           @RequestParam String fromAccount,
                           @RequestParam String toAccount,
                           @RequestParam long amountMinor,
                           @RequestParam String currency) {
    return orchestrator.initiate(customerId, fromAccount, toAccount, amountMinor, currency);
  }
}
