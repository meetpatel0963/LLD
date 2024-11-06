package com.example.tollservice.service;

import com.example.tollservice.model.EntryLog;
import com.example.tollservice.repository.EntryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EntryLogServiceImpl implements EntryLogService {

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Autowired
    private PassService passService;

    @Override
    public EntryLog recordEntry(UUID vehicleId, UUID tollPlazaId, UUID passId) {
        boolean isAllowed = passId != null && passService.validatePass(passId);

        EntryLog entryLog = new EntryLog();
        entryLog.setVehicleId(vehicleId);
        entryLog.setTollPlazaId(tollPlazaId);
        entryLog.setPassId(passId);
        entryLog.setTimestamp(LocalDateTime.now());
        entryLog.setAllowed(isAllowed);

        return entryLogRepository.save(entryLog);
    }
}

