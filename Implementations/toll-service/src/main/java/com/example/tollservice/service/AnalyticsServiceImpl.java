package com.example.tollservice.service;

import com.example.tollservice.model.Transaction;
import com.example.tollservice.repository.EntryLogRepository;
import com.example.tollservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Override
    public BigDecimal getTotalCollectionForPlaza(UUID tollPlazaId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return transactionRepository.findByTollPlazaIdAndDate(tollPlazaId, startOfDay, endOfDay)
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public int getTotalVehiclesForPlaza(UUID tollPlazaId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return entryLogRepository.countByTollPlazaIdAndDate(tollPlazaId, startOfDay, endOfDay);
    }

    @Override
    public int getTotalPassesIssuedForPlaza(UUID tollPlazaId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return transactionRepository.findByTollPlazaIdAndDate(tollPlazaId, startOfDay, endOfDay)
                .stream()
                .map(Transaction::getPass)
                .distinct()
                .toList()
                .size();
    }
}
