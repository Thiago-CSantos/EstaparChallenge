package com.estapar.parking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SECTOR")
public class SectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE_BASE")
    private Double priceBase;

    @Column(name = "MAX_CAPACITY")
    private Integer maxCapacity;

    @Column(name = "OPEN_HOUR")
    private LocalDateTime openHour;

    @Column(name = "CLOSE_HOUR")
    private LocalDateTime closeHour;

    @Column(name = "DURATION_LIMIT_MINUTES")
    private Integer durationLimitMinutes;

    @ManyToOne
    @JoinColumn(name = "GARAGE_ID")
    private GarageEntity garage;

    @OneToMany(mappedBy = "sector")
    private List<ParkingSpotEntity> parkingSpots;

    public SectorEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(Double priceBase) {
        this.priceBase = priceBase;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalDateTime getOpenHour() {
        return openHour;
    }

    public void setOpenHour(LocalDateTime openHour) {
        this.openHour = openHour;
    }

    public LocalDateTime getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(LocalDateTime closeHour) {
        this.closeHour = closeHour;
    }

    public Integer getDurationLimitMinutes() {
        return durationLimitMinutes;
    }

    public void setDurationLimitMinutes(Integer durationLimitMinutes) {
        this.durationLimitMinutes = durationLimitMinutes;
    }

    public GarageEntity getGarage() {
        return garage;
    }

    public void setGarage(GarageEntity garage) {
        this.garage = garage;
    }

    public List<ParkingSpotEntity> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpotEntity> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

}
