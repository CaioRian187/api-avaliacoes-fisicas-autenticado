package com.CaioRian.AvaliacoesFisicas.repository;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CircunferenciasRepository extends JpaRepository<Circunferencias, Long>{

    @Query("SELECT c FROM Circunferencias c WHERE c.aluno.id = :alunoId")
    List<Circunferencias> findAllByAlunoId(@Param("alunoId") UUID alunoId);
}
