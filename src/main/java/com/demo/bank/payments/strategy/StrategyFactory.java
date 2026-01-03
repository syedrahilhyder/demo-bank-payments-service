package com.demo.bank.payments.strategy;

import com.demo.bank.payments.strategy.impl.InternationalTransferStrategy;
import com.demo.bank.payments.strategy.impl.LocalTransferStrategy;
import com.demo.bank.payments.strategy.impl.UtilityPaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {

  private final LocalTransferStrategy local;
  private final InternationalTransferStrategy intl;
  private final UtilityPaymentStrategy utility;

  public StrategyFactory(LocalTransferStrategy local, InternationalTransferStrategy intl, UtilityPaymentStrategy utility) {
    this.local = local;
    this.intl = intl;
    this.utility = utility;
  }

  public PaymentStrategy resolve(String kind) {
    return switch (kind) {
      case "LOCAL" -> local;
      case "INTERNATIONAL" -> intl;
      case "UTILITY" -> utility;
      default -> throw new IllegalArgumentException("Unsupported kind: " + kind);
    };
  }
}
