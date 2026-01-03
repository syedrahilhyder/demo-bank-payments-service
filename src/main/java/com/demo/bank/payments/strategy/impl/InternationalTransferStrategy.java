package com.demo.bank.payments.strategy.impl;

import com.demo.bank.payments.connectors.AccountConnector;
import com.demo.bank.payments.domain.Payment;
import com.demo.bank.payments.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class InternationalTransferStrategy implements PaymentStrategy {

  private final AccountConnector account;

  public InternationalTransferStrategy(AccountConnector account) {
    this.account = account;
  }

  @Override
  public void preProcess(Payment p) {
    p.attachHold(account.placeHold(p.getFromAccount(), p.getAmountMinor(), "INTL_PREAUTH"));
    p.markAmlPending();
  }

  @Override
  public void execute(Payment p) {}

  @Override
  public void postProcess(Payment p) {}
}
