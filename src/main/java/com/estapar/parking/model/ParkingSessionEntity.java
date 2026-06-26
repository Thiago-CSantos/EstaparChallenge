package com.estapar.parking.model;

import com.estapar.parking.enums.SessionStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARKING_SESSION")
public class ParkingSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LICENSE_PLATE")
    private String licensePlate;

    @Column(name = "ENTRY_TIME")
    private LocalDateTime entryTime;

    @Column(name = "EXIT_TIME")
    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    private SessionStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "PARKING_SPOT_ID")
    private ParkingSpotEntity spot;

    @Column(name = "PRICE_CALCULATED")
    private BigDecimal priceCalculated;

    @Column(name = "APPLIED_DISCOUNT")
    private BigDecimal appliedDiscount;

    @Column(name = "FINAL_PRICE")
    private BigDecimal finalPrice;

    public ParkingSessionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public SessionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SessionStatusEnum status) {
        this.status = status;
    }

    public ParkingSpotEntity getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotEntity spot) {
        this.spot = spot;
    }

    public BigDecimal getPriceCalculated() {
        return priceCalculated;
    }

    public void setPriceCalculated(BigDecimal priceCalculated) {
        this.priceCalculated = priceCalculated;
    }

    public BigDecimal getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(BigDecimal appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
