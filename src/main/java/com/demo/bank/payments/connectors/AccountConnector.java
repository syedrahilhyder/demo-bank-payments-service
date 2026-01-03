package com.demo.bank.payments.connectors;

import java.util.UUID;

public interface AccountConnector {
  UUID placeHold(String accountId, long amountMinor, String reason);
  void debit(String accountId, long amountMinor);
  void credit(String accountId, long amountMinor);
}
