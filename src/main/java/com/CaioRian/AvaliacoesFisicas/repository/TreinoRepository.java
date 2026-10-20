package com.CaioRian.AvaliacoesFisicas.repository;

import com.CaioRian.AvaliacoesFisicas.models.entities.Treino;
import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TreinoRepository extends JpaRepository<Treino, UUID> {

    List<Treino> findAllByAlunoId(UUID alunoId);

    List<Treino> findAllByAlunoIdAndDiaSemana(UUID alunoId, DiaSemanaEnum diaSemana);
}
