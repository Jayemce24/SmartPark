package com.smartpark.app.controller;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.model.Vehicle;
import com.smartpark.app.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final MessageProperties messages;

    public VehicleController(VehicleService vehicleService, MessageProperties messages) {
        this.vehicleService = vehicleService;
        this.messages = messages;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.registerVehicle(vehicle);

        Map<String, Object> body = new HashMap<>();
        body.put("message", messages.getVehicleRegistered());
        body.put("vehicle", new VehicleDTO(savedVehicle));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/parked")
    public ResponseEntity<List<VehicleDTO>> getAllParkedVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllParkedVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
