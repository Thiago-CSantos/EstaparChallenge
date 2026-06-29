package com.estapar.parking.service;

import com.estapar.parking.enums.SessionStatusEnum;
import com.estapar.parking.model.ParkingSessionEntity;
import com.estapar.parking.model.ParkingSpotEntity;
import com.estapar.parking.model.SectorEntity;
import com.estapar.parking.repository.ParkingSessionRepository;
import com.estapar.parking.repository.ParkingSpotRepository;
import com.estapar.parking.request.WebhookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class WebhookService {

    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    private ParkingSessionRepository sessionRepository;

    @Transactional
    public void handleEntry(WebhookRequest payload) {

        if (validateSession(payload.licensePlate())) {
            throw new RuntimeException("Ja existe sessao ativa para a placa: " + payload.licensePlate());
        }

        ParkingSpotEntity parkingSpot = spotRepository.findFirstByOccupiedFalse()
                .orElseThrow(
                        () -> new RuntimeException("Estacionamento cheio")
                );

        SectorEntity sectorEntity = parkingSpot.getSector();
        BigDecimal basePrice = sectorEntity.getPriceBase();

        // Desconto aplicado
        BigDecimal discount = calculateDiscount(basePrice, sectorEntity);
        BigDecimal priceCalculated = basePrice.add(discount); // preco que ficara "congelado"

        ParkingSessionEntity sessionEntity = new ParkingSessionEntity();

        sessionEntity.setLicensePlate(payload.licensePlate());
        sessionEntity.setEntryTime(parseDateTime(payload.entryTime()));
        sessionEntity.setStatus(SessionStatusEnum.ACTIVE);
        sessionEntity.setPriceCalculated(priceCalculated);
        sessionEntity.setAppliedDiscount(discount);

        // ainda não existe preço final
        sessionEntity.setFinalPrice(BigDecimal.ZERO);

        sessionRepository.save(sessionEntity);

    }

    @Transactional
    public void handleParked(WebhookRequest payload) {

        ParkingSpotEntity parkingSpot = spotRepository.findByLatAndLng(payload.lat(), payload.lng())
                .orElseThrow(
                        () -> new RuntimeException("Vaga nao encontrada")
                );

        if (parkingSpot.isOccupied()) {
            throw new RuntimeException("Vaga ja esta ocupada");
        }

        ParkingSessionEntity parkingSession = sessionRepository
                .findByLicensePlateAndStatus(payload.licensePlate(), SessionStatusEnum.ACTIVE)
                .orElseThrow(
                        () -> new RuntimeException("Sessao nao encontrada")
                );

        parkingSpot.setOccupied(true);
        parkingSession.setStatus(SessionStatusEnum.PARKED);
        parkingSession.setSpot(parkingSpot);

        spotRepository.save(parkingSpot);
        sessionRepository.save(parkingSession);

    }

    @Transactional
    public void handleExit(WebhookRequest payload) {

        ParkingSessionEntity session = sessionRepository
                .findByLicensePlateAndStatus(payload.licensePlate(), SessionStatusEnum.PARKED)
                .orElseThrow(
                        () -> new RuntimeException("Sessao nao encontrada para placa: " + payload.licensePlate())
                );

        LocalDateTime entryTime = session.getEntryTime();
        LocalDateTime exitTime = parseDateTime(payload.exitTime());

        long minutes = Duration.between(entryTime, exitTime).toMinutes();

        BigDecimal finalPrice = BigDecimal.ZERO;
        if (minutes <= 30) {
            session.setFinalPrice(BigDecimal.ZERO);
        } else {
            long hours = (long) Math.ceil(minutes / 60.0);
            finalPrice = session.getPriceCalculated().multiply(BigDecimal.valueOf(hours));
        }

        ParkingSpotEntity spot = session.getSpot();
        spot.setOccupied(false);
        spotRepository.save(spot);

        session.setExitTime(exitTime);
        session.setFinalPrice(finalPrice);
        session.setStatus(SessionStatusEnum.EXIT);
        sessionRepository.save(session);

    }

    private BigDecimal calculateDiscount(BigDecimal basePrice, SectorEntity sector) {
        double capacity = (double) spotRepository.countOccupiedBySector(sector.getId()) / sector.getMaxCapacity();

        if (capacity < 0.25) {
            return basePrice.multiply(BigDecimal.valueOf(0.10)).negate(); // -10% desconto
        } else if (capacity < 0.50) {
            return BigDecimal.ZERO; // sem desconto
        } else if (capacity < 0.75) {
            return basePrice.multiply(BigDecimal.valueOf(0.10)); // +10% desconto
        } else {
            return basePrice.multiply(BigDecimal.valueOf(0.25)); // +25% desconto
        }
    }

    private boolean validateSession(String licensePlate) {
        return sessionRepository.existsByLicensePlateAndStatus(
                licensePlate, SessionStatusEnum.ACTIVE);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        if (dateTime.endsWith("Z") || dateTime.contains("+")) {
            return ZonedDateTime.parse(dateTime).toLocalDateTime();
        }
        return LocalDateTime.parse(dateTime);
    }

}
