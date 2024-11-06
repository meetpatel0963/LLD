package com.example.tollservice.service;

import com.example.tollservice.model.TollBooth;
import com.example.tollservice.model.TollPlaza;
import com.example.tollservice.repository.TollBoothRepository;
import com.example.tollservice.repository.TollPlazaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TollPlazaServiceImpl implements TollPlazaService {

    @Autowired
    private TollPlazaRepository tollPlazaRepository;

    @Autowired
    private TollBoothRepository tollBoothRepository;

    @Override
    public TollPlaza createTollPlaza(String location) {
        TollPlaza tollPlaza = new TollPlaza();
        tollPlaza.setLocation(location);
        return tollPlazaRepository.save(tollPlaza);
    }

    @Override
    public TollBooth addTollBoothToPlaza(UUID tollPlazaId) {
        TollPlaza tollPlaza = tollPlazaRepository.findById(tollPlazaId)
                .orElseThrow(() -> new EntityNotFoundException("Toll Plaza not found"));
        TollBooth tollBooth = new TollBooth();
        tollBooth.setTollPlaza(tollPlaza);
        return tollBoothRepository.save(tollBooth);
    }

    @Override
    public List<TollBooth> getBoothsByPlaza(UUID tollPlazaId) {
        return tollBoothRepository.findByTollPlazaId(tollPlazaId);
    }

    @Override
    public Optional<TollPlaza> findPlazaById(UUID tollPlazaId) {
        return tollPlazaRepository.findById(tollPlazaId);
    }
}

