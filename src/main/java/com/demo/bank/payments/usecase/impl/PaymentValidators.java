package com.demo.bank.payments.usecase.impl;

import org.springframework.stereotype.Component;

@Component
public class PaymentValidators {

  public void validateCommon(String customerId, long amountMinor, String currency) {
    requireNonBlank(customerId, "customerId");
    requirePositive(amountMinor, "amountMinor");
    requireIso3(currency);
  }

  public void validateAccounts(String from, String to) {
    requireNonBlank(from, "fromAccount");
    requireNonBlank(to, "toAccount");
    if (from.equals(to)) throw new IllegalArgumentException("from/to must differ");
  }

  public void validateInternational(String iban, String bic) {
    requireNonBlank(iban, "beneficiaryIban");
    requireNonBlank(bic, "swiftBic");
    if (iban.length() < 10) throw new IllegalArgumentException("iban too short");
    if (bic.length() < 8) throw new IllegalArgumentException("bic too short");
  }

  public void validateUtility(String biller, String ref) {
    requireNonBlank(biller, "billerCode");
    requireNonBlank(ref, "billRef");
  }

  private void requireIso3(String c) {
    if (c == null || c.length() != 3) throw new IllegalArgumentException("currency must be ISO3");
  }

  private void requirePositive(long v, String name) {
    if (v <= 0) throw new IllegalArgumentException(name + " must be > 0");
  }

  private void requireNonBlank(String v, String name) {
    if (v == null || v.isBlank()) throw new IllegalArgumentException(name + " required");
  }
}
