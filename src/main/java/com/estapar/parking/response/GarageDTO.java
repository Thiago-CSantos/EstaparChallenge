package com.estapar.parking.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GarageDTO(
        @JsonProperty("garage")
        List<SectorDTO>  sector,
        List<SpotsDTO> spots
) {
}
