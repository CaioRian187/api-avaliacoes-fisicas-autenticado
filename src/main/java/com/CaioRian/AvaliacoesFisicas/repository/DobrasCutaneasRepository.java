package com.CaioRian.AvaliacoesFisicas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CaioRian.AvaliacoesFisicas.models.entities.DobrasCutaneas;

public interface DobrasCutaneasRepository extends JpaRepository<DobrasCutaneas, Long>{
    
    List<DobrasCutaneas> findByAluno_id(UUID id);

    @Query("SELECT d FROM DobrasCutaneas d WHERE d.aluno.id = :alunoId AND d.data = :data")
    List<DobrasCutaneas> findByAlunoIdAndData(@Param("alunoId") UUID alunoId, @Param("data") LocalDate data);

    Optional<DobrasCutaneas> findFirstByAluno_IdAndDataOrderByIdAsc(UUID alunoId, LocalDate data);
}
