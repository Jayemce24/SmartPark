package com.smartpark.app.dto;

import com.smartpark.app.model.Vehicle;

public class VehicleDTO {
    private String licensePlate;
    private String type;
    private String ownerName;

    public VehicleDTO(Vehicle vehicle) {
        this.licensePlate = vehicle.getLicensePlate();
        this.type = vehicle.getType().name();
        this.ownerName = vehicle.getOwnerName();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
