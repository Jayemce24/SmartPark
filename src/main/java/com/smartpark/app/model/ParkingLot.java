package com.smartpark.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ParkingLot {

    @Id
    @NotBlank
    @Column(name = "lot_id")
    @Size(max = 50)
    private String lotId;

    @NotBlank
    private String location;

    @Min(1)
    private int capacity;

    @Min(0)
    private int occupiedSpaces;

    @JsonManagedReference
    @OneToMany(mappedBy = "parkingLot")
    private List<Vehicle> vehicles = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.lotId == null || this.lotId.isEmpty()) {
            this.lotId = UUID.randomUUID().toString();
        }
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(int occupiedSpaces) {
        this.occupiedSpaces = occupiedSpaces;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}

