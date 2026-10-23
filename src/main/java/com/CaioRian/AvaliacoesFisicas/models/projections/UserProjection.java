package com.CaioRian.AvaliacoesFisicas.models.projections;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;

import java.util.UUID;

public record UserProjection(
        UUID id,
        String nome,
        Integer idade,
        GeneroEnum genero
) {
}
