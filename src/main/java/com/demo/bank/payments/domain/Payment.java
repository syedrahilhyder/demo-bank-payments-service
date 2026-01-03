package com.demo.bank.payments.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  private UUID id;

  private String customerId;
  private String fromAccount;
  private String toAccount;
  private long amountMinor;
  private String currency;
  private String status;
  private Instant createdAt;

  protected Payment() {}

  public Payment(UUID id, String customerId, String fromAccount, String toAccount,
                 long amountMinor, String currency, String status) {
    this.id = id;
    this.customerId = customerId;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amountMinor = amountMinor;
    this.currency = currency;
    this.status = status;
    this.createdAt = Instant.now();
  }

  public UUID getId() { return id; }
}
