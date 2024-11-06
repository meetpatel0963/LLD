package com.example.tollservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class EntryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID vehicleId;

    @Column(nullable = false)
    private UUID tollPlazaId;

    private UUID passId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean isAllowed;

}

