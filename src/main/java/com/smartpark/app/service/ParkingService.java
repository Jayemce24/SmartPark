package com.smartpark.app.service;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.VehicleDTO;

import com.smartpark.app.exception.*;
import com.smartpark.app.model.ParkingLot;

import com.smartpark.app.model.Vehicle;
import com.smartpark.app.repository.ParkingLotRepository;
import com.smartpark.app.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final MessageProperties messages;

    public ParkingService(ParkingLotRepository parkingLotRepository,
                          VehicleRepository vehicleRepository,
                          VehicleService vehicleService,
                          MessageProperties messages) {
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.messages = messages;
    }

    public ParkingLot registerLot(ParkingLot lot) {
        lot.setLotId(null); // ignore LotId from client
        lot.setOccupiedSpaces(0);
        return parkingLotRepository.save(lot);
    }

    public ParkingLot getLotStatus(String lotId) {
        return parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ParkingLotNotFoundException(messages.getParkingLotNotFound() + ": " +  lotId));
    }

    public List<VehicleDTO> getVehiclesInLot(String lotId) {
        ParkingLot lot = getLotStatus(lotId);
        return lot.getVehicles()
                .stream()
                .map(VehicleDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void checkInVehicle(String lotId, String licensePlate) {
        ParkingLot lot = getLotStatus(lotId);
        Vehicle vehicle = vehicleService.getVehicleIfExists(licensePlate);

        if (vehicle.getParkingLot() != null) {
            throw new VehicleAlreadyParkedException(messages.getVehicleAlreadyParked());
        }

        if (lot.getOccupiedSpaces() >= lot.getCapacity()) {
            throw new ParkingLotFullException(messages.getLotFull());
        }

        vehicle.setParkingLot(lot);
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() + 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(lot);
    }

    @Transactional
    public void checkOutVehicle(String lotId, String licensePlate) {
        ParkingLot lot = getLotStatus(lotId);
        Vehicle vehicle = vehicleService.getVehicleIfExists(licensePlate);

        if (vehicle.getParkingLot() == null) {
            throw new VehicleNotParkedException(messages.getVehicleNotParked());
        }

        if (!lot.getVehicles().contains(vehicle)) {
            throw new IllegalStateException(messages.getVehicleNotInLot());
        }

        vehicle.setParkingLot(null);
        lot.setOccupiedSpaces(lot.getOccupiedSpaces() - 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(lot);
    }

}
