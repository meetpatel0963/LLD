package com.example.tollservice.repository;

import com.example.tollservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.tollBooth.tollPlaza.id = :tollPlazaId AND t.timestamp BETWEEN :startOfDay AND :endOfDay")
    List<Transaction> findByTollPlazaIdAndDate(UUID tollPlazaId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
