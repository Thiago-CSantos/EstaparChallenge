package com.estapar.parking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RevenueRequest(
        @JsonProperty("date")
        String date,
        @JsonProperty("sector")
        String sector
) {
}
