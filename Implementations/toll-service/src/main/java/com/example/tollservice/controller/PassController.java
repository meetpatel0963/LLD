package com.example.tollservice.controller;

import com.example.tollservice.constant.Routes;
import com.example.tollservice.model.Pass;
import com.example.tollservice.model.PassType;
import com.example.tollservice.service.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.BASE_API)
public class PassController {

    @Autowired
    private PassService passService;

    // Endpoint to issue a new pass
    @PostMapping(Routes.ISSUE_PASS)
    public ResponseEntity<Pass> issuePass(
            @RequestParam UUID vehicleId,
            @RequestParam PassType type) {
        Pass pass = passService.issuePass(vehicleId, type);
        return ResponseEntity.ok(pass);
    }

    // Endpoint to validate a pass
    @GetMapping(Routes.VALIDATE_PASS)
    public ResponseEntity<Boolean> validatePass(
            @RequestParam UUID passId,
            @RequestParam UUID tollBoothId) {
        boolean isValid = passService.validatePass(passId, tollBoothId);
        return ResponseEntity.ok(isValid);
    }

    // Endpoint to find a pass by ID
    @GetMapping(Routes.GET_PASS_BY_ID)
    public ResponseEntity<Pass> findPassById(@PathVariable UUID passId) {
        Optional<Pass> pass = passService.findPassById(passId);
        return pass.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
