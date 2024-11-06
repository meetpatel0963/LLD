package com.example.tollservice.repository;

import com.example.tollservice.model.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PassRepository extends JpaRepository<Pass, UUID> {

}

