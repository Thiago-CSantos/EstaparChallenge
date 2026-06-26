package com.estapar.parking.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotsDTO(

        @JsonProperty("id")
        Long id,

        @JsonProperty("sector")
        String sector,

        @JsonProperty("lat")
        Double lat,

        @JsonProperty("lng")
        Double lng,

        @JsonProperty("occupied")
        Boolean occupied
) {
}
