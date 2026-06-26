package com.estapar.parking.repository;

import com.estapar.parking.model.ParkingSpotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {
}
