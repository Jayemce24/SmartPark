package com.smartpark.app.controller;

import com.smartpark.app.config.MessageProperties;
import com.smartpark.app.dto.ParkingLotDTO;
import com.smartpark.app.dto.VehicleDTO;
import com.smartpark.app.model.ParkingLot;
import com.smartpark.app.service.ParkingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    private final ParkingService parkingService;
    private final MessageProperties messages;

    public ParkingLotController(ParkingService parkingService, MessageProperties messages) {
        this.parkingService = parkingService;
        this.messages = messages;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerLot(@RequestBody ParkingLot lot) {
        ParkingLot savedLot = parkingService.registerLot(lot);

        Map<String, Object> response = new HashMap<>();
        response.put("message", messages.getLotRegistered());
        response.put("lot", savedLot);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLotDTO> getStatus(@PathVariable String lotId) {
        ParkingLotDTO parkingLotDto = new ParkingLotDTO(parkingService.getLotStatus(lotId));
        return ResponseEntity.ok(parkingLotDto);
    }

    @GetMapping("/{lotId}/vehicles")
    public ResponseEntity<List<VehicleDTO>> getVehicles(@PathVariable String lotId) {
        List<VehicleDTO> vehicles = parkingService.getVehiclesInLot(lotId);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/{lotId}/checkin")
    public ResponseEntity<Map<String, String>> checkInVehicle(
            @PathVariable String lotId,
            @RequestParam String licensePlate) {

        parkingService.checkInVehicle(lotId, licensePlate);

        Map<String, String> body = new HashMap<>();
        body.put("message", messages.getVehicleCheckedIn());
        body.put("lotId", lotId);
        body.put("licensePlate", licensePlate);

        return ResponseEntity.ok(body);
    }


    @PostMapping("/{lotId}/checkout")
    public ResponseEntity<Map<String, String>> checkOutVehicle(
            @PathVariable String lotId,
            @RequestParam String licensePlate) {

        parkingService.checkOutVehicle(lotId, licensePlate);

        Map<String, String> body = new HashMap<>();
        body.put("message", messages.getVehicleCheckedOut());
        body.put("lotId", lotId);
        body.put("licensePlate", licensePlate);

        return ResponseEntity.ok(body);
    }


}

