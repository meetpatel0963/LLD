package com.example.tollservice.service;

import com.example.tollservice.model.Pass;
import com.example.tollservice.model.Transaction;

import java.util.UUID;

public interface TransactionService {
    Transaction recordTransaction(UUID vehicleId, UUID tollBoothId, Pass pass);
}
