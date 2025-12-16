package com.smartpark.app.service;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.exception.VehicleNotFoundException;
import com.smartpark.app.model.Vehicle;
import com.smartpark.app.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.smartpark.app.exception.LicensePlateAlreadyExistsException;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final MessageProperties messages;

    public VehicleService(VehicleRepository vehicleRepository, MessageProperties messages) {
        this.vehicleRepository = vehicleRepository;
        this.messages = messages;
    }

    public Vehicle registerVehicle(Vehicle vehicle) {

        if (vehicleRepository.existsById(vehicle.getLicensePlate())) {
            throw new LicensePlateAlreadyExistsException(messages.getDuplicateLicensePlate());
        }

        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleIfExists(String licensePlate) {
        return vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundException(messages.getVehicleNotFound() + ": " + licensePlate));
    }

    public List<VehicleDTO> getAllParkedVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .filter(v -> v.getParkingLot() != null)
                .map(VehicleDTO::new)
                .collect(Collectors.toList());
    }
}
