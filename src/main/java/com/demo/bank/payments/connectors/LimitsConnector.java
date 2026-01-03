package com.demo.bank.payments.connectors;

public interface LimitsConnector { boolean allow(String customerId, long amountMinor); }
