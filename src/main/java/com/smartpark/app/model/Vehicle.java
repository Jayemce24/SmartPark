package com.smartpark.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartpark.app.enums.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Vehicle {

    @Id
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate can only contain letters, numbers, and dashes")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name can only contain letters and spaces")
    @Size(max = 100)
    private String ownerName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null) {
            this.type = VehicleType.valueOf(type.toUpperCase());
        }
    }

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
