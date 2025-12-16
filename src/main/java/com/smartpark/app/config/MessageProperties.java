package com.smartpark.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageProperties {

    @Value("${parking.lot.registered}")
    private String lotRegistered;

    @Value("${vehicle.registered}")
    private String vehicleRegistered;

    @Value("${parking.vehicle.checkedin}")
    private String vehicleCheckedIn;

    @Value("${parking.vehicle.checkedout}")
    private String vehicleCheckedOut;

    @Value("${parking.vehicle.notfound}")
    private String vehicleNotFound;

    @Value("${parking.lot.full}")
    private String lotFull;

    @Value("${duplicate.license.plate}")
    private String duplicateLicensePlate;

    @Value("${parking.lot.not.found}")
    private String parkingLotNotFound;

    @Value("${vehicle.not.in.lot}")
    private String vehicleNotInLot;

    @Value("${vehicle.already.parked}")
    private String vehicleAlreadyParked;

    @Value("${vehicle.not.parked}")
    private String vehicleNotParked;

    public String getLotRegistered() { return lotRegistered; }

    public String getVehicleRegistered() {
        return vehicleRegistered;
    }

    public String getVehicleCheckedIn() {
        return vehicleCheckedIn;
    }

    public String getVehicleCheckedOut() {
        return vehicleCheckedOut;
    }

    public String getVehicleNotFound() {
        return vehicleNotFound;
    }

    public String getLotFull() {
        return lotFull;
    }

    public String getDuplicateLicensePlate() {
        return duplicateLicensePlate;
    }

    public String getParkingLotNotFound() {
        return parkingLotNotFound;
    }

    public String getVehicleNotInLot() {
        return vehicleNotInLot;
    }

    public String getVehicleAlreadyParked() { return vehicleAlreadyParked; }

    public String getVehicleNotParked() { return vehicleNotParked; }
}
