package com.CaioRian.AvaliacoesFisicas.repository;

import com.CaioRian.AvaliacoesFisicas.models.entities.TreinoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, UUID> {
}
