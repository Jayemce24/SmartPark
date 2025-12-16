package com.smartpark.app.exception;

public class VehicleNotParkedException extends RuntimeException {
    public VehicleNotParkedException(String message) {
        super(message);
    }
}
