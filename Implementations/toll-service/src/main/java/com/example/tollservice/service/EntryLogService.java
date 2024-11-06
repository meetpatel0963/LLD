package com.example.tollservice.service;

import com.example.tollservice.model.EntryLog;

import java.util.UUID;

public interface EntryLogService {
    EntryLog recordEntry(UUID vehicleId, UUID tollBoothId, UUID passId);
}
