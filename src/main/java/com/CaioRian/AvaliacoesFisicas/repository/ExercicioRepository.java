package com.CaioRian.AvaliacoesFisicas.repository;

import com.CaioRian.AvaliacoesFisicas.models.entities.Exercicio;
import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExercicioRepository extends JpaRepository<Exercicio, UUID> {

    List<Exercicio> findAllByGrupoMuscular(GrupoMuscularEnum grupoMuscular);
}
