package com.demo.bank.payments.strategy;

import com.demo.bank.payments.domain.Payment;

public interface PaymentStrategy {
  void preProcess(Payment p);
  void execute(Payment p);
  void postProcess(Payment p);
}
