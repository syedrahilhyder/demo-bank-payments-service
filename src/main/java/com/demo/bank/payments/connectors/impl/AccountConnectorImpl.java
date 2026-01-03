package com.demo.bank.payments.connectors.impl;

import com.demo.bank.payments.config.DemoServicesProperties;
import com.demo.bank.payments.connectors.AccountConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class AccountConnectorImpl implements AccountConnector {

  private final RestClient rest;
  private final DemoServicesProperties props;

  public AccountConnectorImpl(RestClient rest, DemoServicesProperties props) {
    this.rest = rest;
    this.props = props;
  }

  @Override
  public UUID placeHold(String accountId, long amountMinor, String reason) {
    String url = props.accountBaseUrl() + "/accounts/" + accountId + "/holds?amountMinor=" + amountMinor + "&reason=" + reason;
    return rest.post().uri(url).body((Object) null).retrieve().body(UUID.class);
  }

  @Override
  public void debit(String accountId, long amountMinor) {
    String url = props.accountBaseUrl() + "/accounts/" + accountId + "/debit?amountMinor=" + amountMinor;
    rest.post().uri(url).body((Object) null).retrieve().toBodilessEntity();
  }

  @Override
  public void credit(String accountId, long amountMinor) {
    String url = props.accountBaseUrl() + "/accounts/" + accountId + "/credit?amountMinor=" + amountMinor;
    rest.post().uri(url).body((Object) null).retrieve().toBodilessEntity();
  }
}
