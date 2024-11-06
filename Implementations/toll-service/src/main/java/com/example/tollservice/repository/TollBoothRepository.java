package com.example.tollservice.repository;

import com.example.tollservice.model.TollBooth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TollBoothRepository extends JpaRepository<TollBooth, UUID> {
    List<TollBooth> findByTollPlazaId(UUID tollPlazaId);
}

