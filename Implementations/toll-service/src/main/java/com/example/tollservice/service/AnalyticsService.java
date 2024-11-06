package com.example.tollservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface AnalyticsService {
    BigDecimal getTotalCollectionForPlaza(UUID tollPlazaId, LocalDate date);
    int getTotalVehiclesForPlaza(UUID tollPlazaId, LocalDate date);
    int getTotalPassesIssuedForPlaza(UUID tollPlazaId, LocalDate date);
}
