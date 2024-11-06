package com.example.tollservice.service;

import com.example.tollservice.model.Pass;
import com.example.tollservice.model.PassType;

import java.util.Optional;
import java.util.UUID;

public interface PassService {
    Pass issuePass(UUID vehicleId, UUID tollBoothId, PassType type);
    boolean validatePass(UUID passId);
    Optional<Pass> findPassById(UUID passId);
}

