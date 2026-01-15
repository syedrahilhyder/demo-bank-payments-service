package com.demo.bank.payments.app;

import com.demo.bank.payments.connectors.AccountConnector;
import com.demo.bank.payments.strategy.PaymentStrategy;
import com.demo.bank.payments.strategy.impl.InternationalTransferStrategy;

public class Test {
    PaymentStrategy ps= new InternationalTransferStrategy(new AccountConnector() {
        public UUID placeHold(String accountId, long amountMinor, String reason) {
            return null;
        }

        public void debit(String accountId, long amountMinor) {

        }

        public void credit(String accountId, long amountMinor) {

        }
    });
    ps.preProcess(null);

}
