package com.CaioRian.AvaliacoesFisicas.models.projections;

import java.util.UUID;

public record UserProjection(
        UUID id,
        String nome,
        Integer idade,
        String sexo
) {
}
