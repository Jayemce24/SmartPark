package com.smartpark.app.dto;

import com.smartpark.app.model.ParkingLot;

public class ParkingLotDTO {
    private String lotId;
    private String location;
    private int capacity;
    private int occupiedSpaces;

    public ParkingLotDTO(ParkingLot lot) {
        this.lotId = lot.getLotId();
        this.location = lot.getLocation();
        this.capacity = lot.getCapacity();
        this.occupiedSpaces = lot.getOccupiedSpaces();
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
}

