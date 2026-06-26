package com.estapar.parking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GARAGE")
public class GarageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DAT_CREATED")
    private LocalDateTime datCreated;

    @OneToMany(mappedBy = "garage")
    private List<SectorEntity> sectors;

    public GarageEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }

    public List<SectorEntity> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorEntity> sectors) {
        this.sectors = sectors;
    }
}
