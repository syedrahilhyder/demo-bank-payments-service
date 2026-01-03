package com.demo.bank.payments.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UtilityPaymentRequest(
    @NotBlank String customerId,
    @NotBlank String fromAccount,
    @NotBlank String billerCode,
    @NotBlank String billRef,
    @Min(1) long amountMinor,
    @NotBlank String currency,
    @NotBlank String reference
) {}
