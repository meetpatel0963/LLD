package com.example.tollservice.controller;

import com.example.tollservice.constant.Routes;
import com.example.tollservice.model.Transaction;
import com.example.tollservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(Routes.BASE_API)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Endpoint to record a new transaction
    @PostMapping(Routes.RECORD_TRANSACTION)
    public ResponseEntity<Transaction> recordTransaction(
            @RequestParam UUID vehicleId,
            @RequestParam UUID tollBoothId,
            @RequestParam UUID passId) {
        Transaction transaction = transactionService.recordTransaction(vehicleId, tollBoothId, passId);
        return ResponseEntity.ok(transaction);
    }
}

