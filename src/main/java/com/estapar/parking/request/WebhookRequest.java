package com.estapar.parking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookRequest(
        @JsonProperty("license_plate")
        String licensePlate,
        @JsonProperty("entry_time")
        String entryTime,
        @JsonProperty("exit_time")
        String exitTime,
        @JsonProperty("event_type")
        String eventType,
        @JsonProperty("lat")
        Double lat,
        @JsonProperty("lng")
        Double lng
) {
}
