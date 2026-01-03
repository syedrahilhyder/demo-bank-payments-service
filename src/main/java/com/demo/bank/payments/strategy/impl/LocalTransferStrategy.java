package com.demo.bank.payments.strategy.impl;

import com.demo.bank.payments.connectors.AccountConnector;
import com.demo.bank.payments.domain.Payment;
import com.demo.bank.payments.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class LocalTransferStrategy implements PaymentStrategy {

  private final AccountConnector account;

  public LocalTransferStrategy(AccountConnector account) {
    this.account = account;
  }

  @Override
  public void preProcess(Payment p) {
    p.attachHold(account.placeHold(p.getFromAccount(), p.getAmountMinor(), "LOCAL_PREAUTH"));
    p.markAmlPending();
  }

  @Override
  public void execute(Payment p) {}

  @Override
  public void postProcess(Payment p) {}
}
