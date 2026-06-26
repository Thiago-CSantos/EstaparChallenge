package com.estapar.parking;

import com.estapar.parking.http.GarageClient;
import com.estapar.parking.model.ParkingSpotEntity;
import com.estapar.parking.model.SectorEntity;
import com.estapar.parking.repository.ParkingSpotRepository;
import com.estapar.parking.repository.SectorRepository;
import com.estapar.parking.response.GarageDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitiateRunner implements CommandLineRunner {

    @Autowired
    private GarageClient garageClient;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    @Override
    public void run(String @NonNull ... args) throws Exception {

        if (sectorRepository.findAll().isEmpty()) {

            GarageDTO response = garageClient.getGarage();

            List<SectorEntity> entityList = response.sector().stream().map(s ->
                    new SectorEntity(
                            s.name(),
                            s.basePrice(),
                            s.maxCapacity(),
                            s.openHour(),
                            s.closeHour(),
                            s.durationLimitMinutes()
                    )
            ).toList();

            List<SectorEntity> sectorEntities = sectorRepository.saveAll(entityList);

            List<ParkingSpotEntity> spotEntityList = response.spots().stream().map(spot ->
                    new ParkingSpotEntity(
                            spot.id(),
                            spot.lat(),
                            spot.lng(),
                            spot.occupied(),
                            sectorEntities.stream()
                                    .filter(f -> f
                                            .getName()
                                            .equals(spot.sector())
                                    )
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Spot não encontrado"))
                    )

            ).toList();

            parkingSpotRepository.saveAll(spotEntityList);
        } else {
            System.out.println("Está vazio");
        }
    }

}
