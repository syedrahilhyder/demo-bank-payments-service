package com.demo.bank.payments.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo.services")
public record DemoServicesProperties(
    String accountBaseUrl,
    String limitsBaseUrl
) {}
