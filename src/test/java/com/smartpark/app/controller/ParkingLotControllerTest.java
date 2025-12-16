package com.smartpark.app.controller;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.ParkingLotDTO;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.model.ParkingLot;
import com.smartpark.app.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParkingLotControllerTest {

    @Mock
    private ParkingService parkingService;

    @Mock
    private MessageProperties messages;

    @InjectMocks
    private ParkingLotController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterLot() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("lot123");
        lot.setCapacity(50);

        when(parkingService.registerLot(any(ParkingLot.class))).thenReturn(lot);
        when(messages.getLotRegistered()).thenReturn("Parking lot successfully registered");

        ResponseEntity<Map<String, Object>> response = controller.registerLot(lot);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertEquals("Parking lot successfully registered", body.get("message"));
        assertEquals(lot, body.get("lot"));

        verify(parkingService, times(1)).registerLot(lot);
        verify(messages, times(1)).getLotRegistered();
    }

    @Test
    void testGetStatus() {
        ParkingLot lot = new ParkingLot();
        lot.setLotId("lot123");
        ParkingLotDTO dto = new ParkingLotDTO(lot);

        when(parkingService.getLotStatus("lot123")).thenReturn(lot);

        ResponseEntity<ParkingLotDTO> response = controller.getStatus("lot123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto.getLotId(), response.getBody().getLotId());

        verify(parkingService, times(1)).getLotStatus("lot123");
    }

    @Test
    void testGetVehicles() {
        VehicleDTO vehicle1 = mock(VehicleDTO.class);
        VehicleDTO vehicle2 = mock(VehicleDTO.class);
        List<VehicleDTO> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(parkingService.getVehiclesInLot("lot123")).thenReturn(vehicles);

        ResponseEntity<List<VehicleDTO>> response = controller.getVehicles("lot123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(parkingService, times(1)).getVehiclesInLot("lot123");
    }

    @Test
    void testCheckInVehicle() {
        when(messages.getVehicleCheckedIn()).thenReturn("Vehicle checked in successfully");

        ResponseEntity<Map<String, String>> response = controller.checkInVehicle("lot123", "ABC-123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertEquals("Vehicle checked in successfully", body.get("message"));
        assertEquals("lot123", body.get("lotId"));
        assertEquals("ABC-123", body.get("licensePlate"));

        verify(parkingService, times(1)).checkInVehicle("lot123", "ABC-123");
    }

    @Test
    void testCheckOutVehicle() {
        when(messages.getVehicleCheckedOut()).thenReturn("Vehicle checked out successfully");

        ResponseEntity<Map<String, String>> response = controller.checkOutVehicle("lot123", "ABC-123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, String> body = response.getBody();
        assertEquals("Vehicle checked out successfully", body.get("message"));
        assertEquals("lot123", body.get("lotId"));
        assertEquals("ABC-123", body.get("licensePlate"));

        verify(parkingService, times(1)).checkOutVehicle("lot123", "ABC-123");
    }
}
