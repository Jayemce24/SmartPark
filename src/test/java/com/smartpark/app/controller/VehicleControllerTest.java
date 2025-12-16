package com.smartpark.app.controller;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.model.Vehicle;
import com.smartpark.app.service.VehicleService;

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


class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @Mock
    private MessageProperties messages;

    @InjectMocks
    private VehicleController controller;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    void testRegisterVehicleSuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC-123");
        vehicle.setType("CAR");

        when(vehicleService.registerVehicle(any(Vehicle.class)))
                .thenReturn(vehicle);
        when(messages.getVehicleRegistered())
                .thenReturn("Vehicle registered successfully");

        ResponseEntity<Map<String, Object>> response =
                controller.registerVehicle(vehicle);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertEquals("Vehicle registered successfully", body.get("message"));

        VehicleDTO dto = (VehicleDTO) body.get("vehicle");
        assertEquals("ABC-123", dto.getLicensePlate());

        verify(vehicleService, times(1)).registerVehicle(vehicle);
        verify(messages, times(1)).getVehicleRegistered();
    }

    @Test
    void testGetAllParkedVehiclesSuccess() {
        VehicleDTO v1 = mock(VehicleDTO.class);
        VehicleDTO v2 = mock(VehicleDTO.class);

        when(vehicleService.getAllParkedVehicles())
                .thenReturn(Arrays.asList(v1, v2));

        ResponseEntity<List<VehicleDTO>> response =
                controller.getAllParkedVehicles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(vehicleService, times(1)).getAllParkedVehicles();
    }
}
