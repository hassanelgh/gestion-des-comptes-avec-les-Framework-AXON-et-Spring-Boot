package com.example.comptequeryside.repositories;

import com.example.comptequeryside.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
