package com.estapar.parking.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private BigDecimal priceBase;

    @Column(name = "MAX_CAPACITY")
    private Integer maxCapacity;

    @Column(name = "OPEN_HOUR")
    private LocalTime openHour;

    @Column(name = "CLOSE_HOUR")
    private LocalTime closeHour;

    @Column(name = "DURATION_LIMIT_MINUTES")
    private Integer durationLimitMinutes;

    @ManyToOne
    @JoinColumn(name = "GARAGE_ID")
    private GarageEntity garage;

    @OneToMany(mappedBy = "sector")
    private List<ParkingSpotEntity> parkingSpots;

    public SectorEntity() {
    }

    public SectorEntity(String name, BigDecimal priceBase, Integer maxCapacity,
                        LocalTime openHour, LocalTime closeHour, Integer durationLimitMinutes) {
        this.name = name;
        this.priceBase = priceBase;
        this.maxCapacity = maxCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.durationLimitMinutes = durationLimitMinutes;
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

    public BigDecimal getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(BigDecimal priceBase) {
        this.priceBase = priceBase;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalTime getOpenHour() {
        return openHour;
    }

    public void setOpenHour(LocalTime openHour) {
        this.openHour = openHour;
    }

    public LocalTime getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(LocalTime closeHour) {
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
