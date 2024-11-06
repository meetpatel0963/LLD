package com.example.tollservice.repository;

import com.example.tollservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

}

