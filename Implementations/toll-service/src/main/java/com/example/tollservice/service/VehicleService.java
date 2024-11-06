package com.example.tollservice.service;

import com.example.tollservice.model.Vehicle;
import com.example.tollservice.model.VehicleType;

import java.util.Optional;
import java.util.UUID;

public interface VehicleService {
    Vehicle registerVehicle(VehicleType type);
    Optional<Vehicle> findVehicleById(UUID vehicleId);
}
