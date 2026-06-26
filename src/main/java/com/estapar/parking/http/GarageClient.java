package com.estapar.parking.http;

import com.estapar.parking.response.GarageDTO;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/")
public interface GarageClient {

    @GetExchange("garage")
    GarageDTO getGarage();

}
