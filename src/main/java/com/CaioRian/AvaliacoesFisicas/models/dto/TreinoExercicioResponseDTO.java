package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;

import java.util.UUID;

public record TreinoExercicioResponseDTO(
        UUID id,
        UUID exercicioId,
        String nomeExercicio,
        GrupoMuscularEnum grupoMuscular,
        Integer quantidadeSeries,
        Integer quantidadeRepeticoes,
        String carga,
        Integer tempoDescanso
) {
}
