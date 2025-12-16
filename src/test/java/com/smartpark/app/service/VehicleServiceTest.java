package com.smartpark.app.service;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.exception.LicensePlateAlreadyExistsException;
import com.smartpark.app.exception.VehicleNotFoundException;
import com.smartpark.app.model.Vehicle;
import com.smartpark.app.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private MessageProperties messages;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void testRegisterVehicleSuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        when(vehicleRepository.existsById("ABC-123")).thenReturn(false);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle saved = vehicleService.registerVehicle(vehicle);

        assertEquals("ABC-123", saved.getLicensePlate());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testGetVehicleIfExistsSuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        when(vehicleRepository.findById("ABC-123")).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleIfExists("ABC-123");

        assertEquals("ABC-123", result.getLicensePlate());
    }

    @Test
    void testGetAllParkedVehiclesSuccess() {
        Vehicle parked = new Vehicle();
        parked.setLicensePlate("PARKED");
        parked.setParkingLot(mock(com.smartpark.app.model.ParkingLot.class));
        parked.setType("CAR");

        Vehicle unparked = new Vehicle();
        unparked.setLicensePlate("UNPARKED");
        unparked.setParkingLot(null);
        unparked.setType("MOTORCYCLE");

        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(parked, unparked));

        List<VehicleDTO> result = vehicleService.getAllParkedVehicles();

        assertEquals(1, result.size());
        assertEquals("PARKED", result.get(0).getLicensePlate());
    }

    @Test
    void testRegisterVehicleDuplicateLicensePlate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");

        when(vehicleRepository.existsById("ABC-123")).thenReturn(true);
        when(messages.getDuplicateLicensePlate()).thenReturn("Duplicate license plate");

        LicensePlateAlreadyExistsException ex = assertThrows(LicensePlateAlreadyExistsException.class,
                () -> vehicleService.registerVehicle(vehicle));

        assertEquals("Duplicate license plate", ex.getMessage());
    }

    @Test
    void testGetVehicleIfExistsNotFound() {
        when(vehicleRepository.findById("ABC-123")).thenReturn(Optional.empty());
        when(messages.getVehicleNotFound()).thenReturn("Vehicle not found");

        VehicleNotFoundException ex = assertThrows(VehicleNotFoundException.class,
                () -> vehicleService.getVehicleIfExists("ABC-123"));

        assertTrue(ex.getMessage().contains("Vehicle not found"));
    }
}
