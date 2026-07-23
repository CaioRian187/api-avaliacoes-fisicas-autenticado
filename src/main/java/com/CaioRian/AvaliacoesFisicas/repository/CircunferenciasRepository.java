package com.CaioRian.AvaliacoesFisicas.repository;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;

public interface CircunferenciasRepository extends JpaRepository<Circunferencias, Long>{

    List<CircunferenciaResponseDto> findByAluno_id(UUID id);

    List<Circunferencias> findAllByAlunoId(UUID alunoId);

    List<Circunferencias> findAllByAluno_Id(UUID alunoId);
}
