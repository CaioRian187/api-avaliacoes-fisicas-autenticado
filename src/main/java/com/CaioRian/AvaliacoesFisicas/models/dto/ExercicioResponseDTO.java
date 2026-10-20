package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;

import java.util.UUID;

public record ExercicioResponseDTO(
        UUID id,
        String nome,
        String descricao,
        GrupoMuscularEnum grupoMuscular,
        String urlVideo
) {
}
