package com.CaioRian.AvaliacoesFisicas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CircunferenciasRepository extends JpaRepository<Circunferencias, Long>{

    @Query("""
        SELECT c FROM Circunferencias c
        WHERE c.aluno.id = :alunoId
        ORDER BY c.data ASC
    """)
    List<Circunferencias> findAllByAlunoId(@Param("alunoId") UUID alunoId);

    @Query("""
        SELECT new com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto(
            c.id,
            c.data,
            c.altura,
            c.peso,
            c.imc,
            c.ombro,
            c.cintura,
            c.quadril,
            c.peitoral,
            c.abdommen,
            c.coxaProximalEsquerda,
            c.coxaProximalDireita,
            c.coxaMedialEsquerda,
            c.coxaMedialDireita,
            c.coxaDistalEsquerda,
            c.coxaDistalDireita,
            c.panturrilhaEsquerda,
            c.panturrilhaDireita,
            c.bracoRelaxadoEsquerdo,
            c.bracoRelaxadoDireito,
            c.bracoContraidoEsquerdo,
            c.bracoContraidoDireito,
            c.antebraçoEsquerdo,
            c.antebraçoDireito,
            a.id,
            a.nome,
            a.idade,
            a.genero
        )
        FROM Circunferencias c
        LEFT JOIN c.aluno a
        WHERE c.id = :id
    """)
    Optional<CircunferenciaResponseDto> findCircunferenciaById(@Param("id") Long id);

    @Query("""
        SELECT new com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto(
            c.id,
            c.data,
            c.altura,
            c.peso,
            c.imc,
            c.ombro,
            c.cintura,
            c.quadril,
            c.peitoral,
            c.abdommen,
            c.coxaProximalEsquerda,
            c.coxaProximalDireita,
            c.coxaMedialEsquerda,
            c.coxaMedialDireita,
            c.coxaDistalEsquerda,
            c.coxaDistalDireita,
            c.panturrilhaEsquerda,
            c.panturrilhaDireita,
            c.bracoRelaxadoEsquerdo,
            c.bracoRelaxadoDireito,
            c.bracoContraidoEsquerdo,
            c.bracoContraidoDireito,
            c.antebraçoEsquerdo,
            c.antebraçoDireito,
            a.id,
            a.nome,
            a.idade,
            a.genero
        )
        FROM Circunferencias c
        LEFT JOIN c.aluno a
        ORDER BY c.data ASC
    """)
    List<CircunferenciaResponseDto> findAllCircunferencias();

    @Query("SELECT c FROM Circunferencias c WHERE c.aluno.id = :alunoId AND c.data = :data")
    List<Circunferencias> findByAlunoIdAndData(@Param("alunoId") UUID alunoId, @Param("data") LocalDate data);

    Optional<Circunferencias> findFirstByAluno_IdAndDataOrderByIdAsc(UUID alunoId, LocalDate data);
}
