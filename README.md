# demo-bank-payments-service

Payments orchestration service.

Responsibilities:
- Initiate payments
- Call account-service (debit)
- Publish PaymentInitiated & PaymentFinalized events
- Act as main orchestrator for demo flows

Tech:
- Spring Boot 3
- Java 21
- Postgres
- Flyway
- Kafka producer
