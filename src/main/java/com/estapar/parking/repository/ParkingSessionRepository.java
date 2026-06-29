package com.estapar.parking.repository;

import com.estapar.parking.enums.SessionStatusEnum;
import com.estapar.parking.model.ParkingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSessionEntity, Long> {

    Optional<ParkingSessionEntity> findByLicensePlateAndStatus(String licensePlate, SessionStatusEnum status);

    boolean existsByLicensePlateAndStatus(String licensePlate, SessionStatusEnum status);

    List<ParkingSessionEntity> findBySpot_Sector_NameAndExitTimeBetweenAndStatus(
            String sectorName,
            LocalDateTime start,
            LocalDateTime end,
            SessionStatusEnum status
    );

}
