package com.demo.bank.payments;

import com.demo.bank.payments.config.DemoServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DemoServicesProperties.class)
public class PaymentsServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(PaymentsServiceApplication.class, args);
  }
}
