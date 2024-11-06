package com.example.tollservice.service;

import com.example.tollservice.model.TollBooth;
import com.example.tollservice.model.TollPlaza;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TollPlazaService {
    TollPlaza createTollPlaza(String location);
    TollBooth addTollBoothToPlaza(UUID tollPlazaId);
    List<TollBooth> getBoothsByPlaza(UUID tollPlazaId);
    Optional<TollPlaza> findPlazaById(UUID tollPlazaId);
}

