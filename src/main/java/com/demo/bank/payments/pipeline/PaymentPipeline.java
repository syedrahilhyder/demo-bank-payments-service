package com.demo.bank.payments.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class PaymentPipeline {

  private static final Logger log = LoggerFactory.getLogger(PaymentPipeline.class);

  public <T> T run(String kind, String reference, Supplier<T> core) {
    String correlationId = "PAY-" + UUID.randomUUID();
    Instant start = Instant.now();

    log.info("pipeline.start kind={} reference={} corr={}", kind, reference, correlationId);

    try {
      T result = core.get();
      log.info("pipeline.ok kind={} corr={} durMs={}", kind, correlationId, msSince(start));
      return result;
    } catch (Exception e) {
      log.warn("pipeline.fail kind={} corr={} err={}", kind, correlationId, e.toString());
      throw e;
    } finally {
      log.info("pipeline.end kind={} corr={}", kind, correlationId);
    }
  }

  private long msSince(Instant start) {
    return Math.max(0, Instant.now().toEpochMilli() - start.toEpochMilli());
  }
}
