package com.example.tollservice.service;

import com.example.tollservice.model.Vehicle;
import com.example.tollservice.model.VehicleType;
import com.example.tollservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle registerVehicle(VehicleType type) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(type);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findVehicleById(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }
}

