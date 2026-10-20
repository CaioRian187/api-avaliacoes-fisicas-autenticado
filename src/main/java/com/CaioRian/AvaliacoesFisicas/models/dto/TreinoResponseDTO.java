package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;

import java.util.List;
import java.util.UUID;

public record TreinoResponseDTO(
        UUID id,
        String nomeTreino,
        String observacoes,
        DiaSemanaEnum diaSemana,
        UUID alunoId,
        String nomeAluno,
        List<TreinoExercicioResponseDTO> exercicios
) {
}
