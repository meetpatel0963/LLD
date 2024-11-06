package com.example.tollservice.service;

import com.example.tollservice.model.Pass;
import com.example.tollservice.model.TollBooth;
import com.example.tollservice.model.Transaction;
import com.example.tollservice.model.Vehicle;
import com.example.tollservice.repository.TollBoothRepository;
import com.example.tollservice.repository.TransactionRepository;
import com.example.tollservice.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TollBoothRepository tollBoothRepository;

    @Override
    @Transactional
    public Transaction recordTransaction(UUID vehicleId, UUID tollBoothId, Pass pass) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        TollBooth tollBooth = tollBoothRepository.findById(tollBoothId)
                .orElseThrow(() -> new EntityNotFoundException("Toll Booth not found"));

        Transaction transaction = new Transaction();
        transaction.setVehicle(vehicle);
        transaction.setTollBooth(tollBooth);
        transaction.setPass(pass);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAmount(pass.getPrice());
        return transactionRepository.save(transaction);
    }
}
