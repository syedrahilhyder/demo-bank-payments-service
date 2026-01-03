package com.demo.bank.payments.api;

import com.demo.bank.payments.api.dto.*;
import com.demo.bank.payments.app.PaymentsFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

  private final PaymentsFacade facade;

  public PaymentsController(PaymentsFacade facade) {
    this.facade = facade;
  }

  @PostMapping("/local")
  public UUID local(@Valid @RequestBody LocalPaymentRequest req) {
    return facade.initiateLocal(req);
  }

  @PostMapping("/international")
  public UUID international(@Valid @RequestBody InternationalPaymentRequest req) {
    return facade.initiateInternational(req);
  }

  @PostMapping("/utility")
  public UUID utility(@Valid @RequestBody UtilityPaymentRequest req) {
    return facade.initiateUtility(req);
  }

  @PostMapping("/{paymentId}/force-finalize")
  public void forceFinalize(@PathVariable UUID paymentId) {
    facade.forceFinalize(paymentId);
  }
}
