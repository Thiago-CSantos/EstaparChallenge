package com.estapar.parking.repository;

import com.estapar.parking.model.ParkingSpotEntity;
import com.estapar.parking.model.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {

    Optional<ParkingSpotEntity> findFirstByOccupiedFalse();

    @Query("SELECT COUNT(ps) FROM ParkingSpotEntity ps WHERE ps.occupied = true AND ps.sector.id = :sectorId")
    Long countOccupiedBySector(@Param("sectorId") Long sectorId);

    Optional<ParkingSpotEntity> findByLatAndLng(Double lat, Double lng);

}
