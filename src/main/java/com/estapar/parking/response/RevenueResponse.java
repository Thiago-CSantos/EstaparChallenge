package com.estapar.parking.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record RevenueResponse(
        @JsonProperty("amount")
        BigDecimal amount,
        @JsonProperty("currency")
        String currency,
        @JsonProperty("timestamp")
        String timestamp
) {
}
