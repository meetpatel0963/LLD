package com.example.tollservice.repository;

import com.example.tollservice.model.EntryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface EntryLogRepository extends JpaRepository<EntryLog, UUID> {

    @Query("SELECT COUNT(e) FROM EntryLog e WHERE e.tollPlazaId = :tollPlazaId AND e.timestamp BETWEEN :startOfDay AND :endOfDay")
    int countByTollPlazaIdAndDate(UUID tollPlazaId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
