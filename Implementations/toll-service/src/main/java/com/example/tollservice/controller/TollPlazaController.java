package com.example.tollservice.controller;

import com.example.tollservice.constant.Routes;
import com.example.tollservice.model.TollBooth;
import com.example.tollservice.model.TollPlaza;
import com.example.tollservice.service.TollPlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.BASE_API)
public class TollPlazaController {

    @Autowired
    private TollPlazaService tollPlazaService;


    // Endpoint to create a new toll plaza
    @PostMapping(Routes.CREATE_TOLL_PLAZA)
    public ResponseEntity<TollPlaza> createTollPlaza(@RequestParam String location) {
        TollPlaza tollPlaza = tollPlazaService.createTollPlaza(location);
        return ResponseEntity.ok(tollPlaza);
    }

    // Endpoint to add a toll booth to a toll plaza
    @PostMapping(Routes.ADD_TOLL_BOOTH)
    public ResponseEntity<TollBooth> addTollBoothToPlaza(@PathVariable UUID tollPlazaId) {
        TollBooth tollBooth = tollPlazaService.addTollBoothToPlaza(tollPlazaId);
        return ResponseEntity.ok(tollBooth);
    }

    // Endpoint to get all booths by plaza ID
    @GetMapping(Routes.GET_TOLLBOOTHS_BY_PLAZA)
    public ResponseEntity<List<TollBooth>> getBoothsByPlaza(@PathVariable UUID tollPlazaId) {
        List<TollBooth> tollBooths = tollPlazaService.getBoothsByPlaza(tollPlazaId);
        return ResponseEntity.ok(tollBooths);
    }

    // Endpoint to get a toll plaza by ID
    @GetMapping(Routes.GET_TOLLPLAZA_BY_ID)
    public ResponseEntity<TollPlaza> getTollPlazaById(@PathVariable UUID tollPlazaId) {
        Optional<TollPlaza> tollPlaza = tollPlazaService.findPlazaById(tollPlazaId);
        return tollPlaza.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
