package com.smartpark.app.service;

import com.smartpark.app.config.MessageProperties;

import com.smartpark.app.exception.ParkingLotFullException;
import com.smartpark.app.exception.ParkingLotNotFoundException;
import com.smartpark.app.exception.VehicleAlreadyParkedException;
import com.smartpark.app.exception.VehicleNotParkedException;
import com.smartpark.app.model.ParkingLot;
import com.smartpark.app.model.Vehicle;
import com.smartpark.app.repository.ParkingLotRepository;
import com.smartpark.app.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private MessageProperties messages;

    @InjectMocks
    private ParkingService parkingService;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void testRegisterLotSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(10);

        when(parkingLotRepository.save(any(ParkingLot.class))).thenAnswer(i -> i.getArgument(0));

        ParkingLot saved = parkingService.registerLot(lot);

        assertEquals(0, saved.getOccupiedSpaces());
        assertNull(saved.getLotId());
        verify(parkingLotRepository, times(1)).save(lot);
    }

    @Test
    void testGetLotStatusSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("lot123");

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));

        ParkingLot result = parkingService.getLotStatus("lot123");

        assertEquals("lot123", result.getLotId());
    }

    @Test
    void testGetVehiclesInLotSuccess() {
        ParkingLot lot = new ParkingLot();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("ABC-123");
        vehicle1.setType("TRUCK");
        lot.setVehicles(Arrays.asList(vehicle1));

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));

        assertEquals(1, parkingService.getVehiclesInLot("lot123").size());
        assertEquals("ABC-123", parkingService.getVehiclesInLot("lot123").get(0).getLicensePlate());
    }

    @Test
    void testCheckInVehicleSuccess() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("lot123");
        lot.setCapacity(2);
        lot.setOccupiedSpaces(1);

        Vehicle vehicle = new Vehicle();

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArgument(0));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenAnswer(i -> i.getArgument(0));

        parkingService.checkInVehicle("lot123", "ABC-123");

        assertEquals(lot, vehicle.getParkingLot());
        assertEquals(2, lot.getOccupiedSpaces());
    }

    @Test
    void testCheckOutVehicleSuccess() {
        ParkingLot lot = new ParkingLot();
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingLot(lot);
        lot.setVehicles(Arrays.asList(vehicle));
        lot.setOccupiedSpaces(1);

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArgument(0));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenAnswer(i -> i.getArgument(0));

        parkingService.checkOutVehicle("lot123", "ABC-123");

        assertNull(vehicle.getParkingLot());
        assertEquals(0, lot.getOccupiedSpaces());
    }


    @Test
    void testGetLotStatusNotFound() {
        when(parkingLotRepository.findById("lotX")).thenReturn(Optional.empty());
        when(messages.getParkingLotNotFound()).thenReturn("Lot not found");

        ParkingLotNotFoundException ex = assertThrows(ParkingLotNotFoundException.class,
                () -> parkingService.getLotStatus("lotX"));

        assertTrue(ex.getMessage().contains("Lot not found"));
    }

    @Test
    void testCheckInVehicleVehicleAlreadyParked() {
        ParkingLot lot = new ParkingLot();
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingLot(new ParkingLot()); // already parked

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(messages.getVehicleAlreadyParked()).thenReturn("Vehicle already parked");

        VehicleAlreadyParkedException ex = assertThrows(VehicleAlreadyParkedException.class,
                () -> parkingService.checkInVehicle("lot123", "ABC-123"));

        assertEquals("Vehicle already parked", ex.getMessage());
    }

    @Test
    void testCheckInVehicleLotFull() {
        ParkingLot lot = new ParkingLot();
        lot.setCapacity(1);
        lot.setOccupiedSpaces(1);
        Vehicle vehicle = new Vehicle();

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(messages.getLotFull()).thenReturn("Lot full");

        ParkingLotFullException ex = assertThrows(ParkingLotFullException.class,
                () -> parkingService.checkInVehicle("lot123", "ABC-123"));

        assertEquals("Lot full", ex.getMessage());
    }

    @Test
    void testCheckOutVehicleVehicleNotParked() {
        ParkingLot lot = new ParkingLot();
        Vehicle vehicle = new Vehicle();

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(messages.getVehicleNotInLot()).thenReturn("Vehicle not in lot");

        VehicleNotParkedException ex = assertThrows(VehicleNotParkedException.class,
                () -> parkingService.checkOutVehicle("lot123", "ABC-123"));

        assertEquals("Vehicle not in lot", ex.getMessage());
    }

    @Test
    void testCheckOutVehicleWrongLot() {
        ParkingLot lot = new ParkingLot();
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingLot(new ParkingLot());
        lot.setVehicles(Arrays.asList());

        when(parkingLotRepository.findById("lot123")).thenReturn(Optional.of(lot));
        when(vehicleService.getVehicleIfExists("ABC-123")).thenReturn(vehicle);
        when(messages.getVehicleNotInLot()).thenReturn("Vehicle not in lot");

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> parkingService.checkOutVehicle("lot123", "ABC-123"));

        assertEquals("Vehicle not in lot", ex.getMessage());
    }
}
