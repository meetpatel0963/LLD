package com.example.tollservice.service;

import com.example.tollservice.config.PassConfig;
import com.example.tollservice.model.Pass;
import com.example.tollservice.model.PassType;
import com.example.tollservice.model.Transaction;
import com.example.tollservice.model.Vehicle;
import com.example.tollservice.repository.PassRepository;
import com.example.tollservice.repository.TransactionRepository;
import com.example.tollservice.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassServiceImpl implements PassService {

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PassConfig passConfig;

    @Override
    @Transactional
    public Pass issuePass(UUID vehicleId, UUID tollBoothId, PassType type) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        Pass pass = new Pass();
        pass.setVehicle(vehicle);
        pass.setType(type);
        pass.setValidFrom(LocalDateTime.now());

        switch (type) {
            case ONE_WAY:
                pass.setValidUntil(LocalDateTime.now().plus(passConfig.getOneWay().getValidity()));
                pass.setPrice(BigDecimal.valueOf(passConfig.getOneWay().getPrice()));
                break;
            case ROUND_TRIP:
                pass.setValidUntil(LocalDateTime.now().plus(passConfig.getRoundTrip().getValidity()));
                pass.setPrice(BigDecimal.valueOf(passConfig.getRoundTrip().getPrice()));
                break;
            case DAILY:
                pass.setValidUntil(LocalDateTime.now().plus(passConfig.getDaily().getValidity()));
                pass.setPrice(BigDecimal.valueOf(passConfig.getDaily().getPrice()));
                break;
            case WEEKLY:
                pass.setValidUntil(LocalDateTime.now().plus(passConfig.getWeekly().getValidity()));
                pass.setPrice(BigDecimal.valueOf(passConfig.getWeekly().getPrice()));
                break;
        }

        Pass savedPass = passRepository.save(pass);
        transactionService.recordTransaction(vehicleId, tollBoothId, pass);
        return savedPass;
    }

    @Override
    public boolean validatePass(UUID passId) {
        Pass pass = passRepository.findById(passId)
                .orElseThrow(() -> new EntityNotFoundException("Pass not found"));

        return pass.getValidUntil().isAfter(LocalDateTime.now());
    }

    @Override
    public Optional<Pass> findPassById(UUID passId) {
        return passRepository.findById(passId);
    }
}

