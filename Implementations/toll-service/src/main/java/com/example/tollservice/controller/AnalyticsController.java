package com.example.tollservice.controller;

import com.example.tollservice.constant.Routes;
import com.example.tollservice.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(Routes.BASE_API)
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // Endpoint to get total collection for a toll plaza on a specific date
    @GetMapping(Routes.TOTAL_COLLECTION_FOR_PLAZA)
    public ResponseEntity<BigDecimal> getTotalCollectionForPlaza(
            @RequestParam UUID tollPlazaId,
            @RequestParam LocalDate date) {
        BigDecimal totalCollection = analyticsService.getTotalCollectionForPlaza(tollPlazaId, date);
        return ResponseEntity.ok(totalCollection);
    }

    // Endpoint to get total vehicles for a toll plaza on a specific date
    @GetMapping(Routes.TOTAL_VEHICLES_FOR_PLAZA)
    public ResponseEntity<Integer> getTotalVehiclesForPlaza(
            @RequestParam UUID tollPlazaId,
            @RequestParam LocalDate date) {
        int totalVehicles = analyticsService.getTotalVehiclesForPlaza(tollPlazaId, date);
        return ResponseEntity.ok(totalVehicles);
    }

    // Endpoint to get total passes issued for a toll plaza on a specific date
    @GetMapping(Routes.TOTAL_PASSES_ISSUED_FOR_PLAZA)
    public ResponseEntity<Integer> getTotalPassesIssuedForPlaza(
            @RequestParam UUID tollPlazaId,
            @RequestParam LocalDate date) {
        int totalPasses = analyticsService.getTotalPassesIssuedForPlaza(tollPlazaId, date);
        return ResponseEntity.ok(totalPasses);
    }
}

