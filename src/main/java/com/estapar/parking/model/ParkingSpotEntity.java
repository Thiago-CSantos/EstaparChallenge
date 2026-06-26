package com.estapar.parking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PARKING_SPOT")
public class ParkingSpotEntity {

    @Id
    private Long id; // vem do simulador

    @Column(name = "LAT")
    private Double lat;

    @Column(name = "LNG")
    private Double lng;

    @Column(name = "OCCUPIED")
    private boolean occupied;

    @ManyToOne
    @JoinColumn(name = "SECTOR_ID")
    private SectorEntity sector;

    public ParkingSpotEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public SectorEntity getSector() {
        return sector;
    }

    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }
}
