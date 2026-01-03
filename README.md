# demo-bank-payments-service (v2)

A more insane payments service:
- Multiple payment types (local, international, utility)
- Internal pipeline + validators + strategy selection
- REST calls to limits-service + account-service
- Kafka produce initiated/finalized events, consume AML results
