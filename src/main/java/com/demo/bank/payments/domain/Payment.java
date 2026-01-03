package com.demo.bank.payments.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  private UUID id;

  private String kind;
  private String customerId;
  private String fromAccount;
  private String toAccount;
  private long amountMinor;
  private String currency;
  private String reference;
  private String status; // INITIATED | AML_PENDING | FINALIZED | FAILED
  private UUID holdId;
  private Instant createdAt;

  protected Payment() {}

  public Payment(UUID id, String kind, String customerId, String fromAccount, String toAccount,
                 long amountMinor, String currency, String reference) {
    this.id = id;
    this.kind = kind;
    this.customerId = customerId;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amountMinor = amountMinor;
    this.currency = currency;
    this.reference = reference;
    this.status = "INITIATED";
    this.createdAt = Instant.now();
  }

  public UUID getId() { return id; }
  public String getKind() { return kind; }
  public String getCustomerId() { return customerId; }
  public String getFromAccount() { return fromAccount; }
  public String getToAccount() { return toAccount; }
  public long getAmountMinor() { return amountMinor; }
  public String getCurrency() { return currency; }
  public String getReference() { return reference; }
  public String getStatus() { return status; }
  public UUID getHoldId() { return holdId; }

  public void markAmlPending() { this.status = "AML_PENDING"; }
  public void markFinalized() { this.status = "FINALIZED"; }
  public void markFailed(String reason) { this.status = "FAILED"; }
  public void attachHold(UUID holdId) { this.holdId = holdId; }
}
