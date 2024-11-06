package com.example.tollservice.controller;

import com.example.tollservice.constant.Routes;
import com.example.tollservice.model.Vehicle;
import com.example.tollservice.model.VehicleType;
import com.example.tollservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.BASE_API)
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Endpoint to register a new vehicle
    @PostMapping(Routes.REGISTER_VEHICLE)
    public ResponseEntity<Vehicle> registerVehicle(@RequestParam VehicleType type) {
        Vehicle vehicle = vehicleService.registerVehicle(type);
        return ResponseEntity.ok(vehicle);
    }

    // Endpoint to find a vehicle by ID
    @GetMapping(Routes.GET_VEHICLE_BY_ID)
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable UUID vehicleId) {
        Optional<Vehicle> vehicle = vehicleService.findVehicleById(vehicleId);
        return vehicle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

