package com.estapar.parking.service;

import com.estapar.parking.enums.SessionStatusEnum;
import com.estapar.parking.model.ParkingSessionEntity;
import com.estapar.parking.repository.ParkingSessionRepository;
import com.estapar.parking.request.RevenueRequest;
import com.estapar.parking.response.RevenueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class RevenueService {

    @Autowired
    private ParkingSessionRepository sessionRepository;

    public RevenueResponse getRevenue(RevenueRequest request) {

        LocalDate date = LocalDate.parse(request.date());
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX).truncatedTo(ChronoUnit.SECONDS);

        List<ParkingSessionEntity> sessionEntityList =
                sessionRepository.findBySpot_Sector_NameAndExitTimeBetweenAndStatus(
                        request.sector(),
                        start,
                        end,
                        SessionStatusEnum.EXIT
                );

        BigDecimal total = sessionEntityList
                .stream()
                .map(s -> s.getFinalPrice())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, (subtotal, preco) -> subtotal.add(preco));

        return new RevenueResponse(
                total,
                "BR",
                ZonedDateTime.now(ZoneOffset.UTC).toString()
        );

    }

}
