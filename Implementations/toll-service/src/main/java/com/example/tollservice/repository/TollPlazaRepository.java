package com.example.tollservice.repository;

import com.example.tollservice.model.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TollPlazaRepository extends JpaRepository<TollPlaza, UUID> {

}

