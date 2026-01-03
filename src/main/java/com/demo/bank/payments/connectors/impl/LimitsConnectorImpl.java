package com.demo.bank.payments.connectors.impl;

import com.demo.bank.payments.config.DemoServicesProperties;
import com.demo.bank.payments.connectors.LimitsConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LimitsConnectorImpl implements LimitsConnector {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public LimitsConnectorImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public boolean allow(String customerId, long amountMinor) {
    String url = props.limitsBaseUrl() + "/limits/check?customerId=" + customerId + "&amountMinor=" + amountMinor;
    Boolean ok = rest.get().uri(url).retrieve().body(Boolean.class);
    return ok != null && ok;
  }
}
