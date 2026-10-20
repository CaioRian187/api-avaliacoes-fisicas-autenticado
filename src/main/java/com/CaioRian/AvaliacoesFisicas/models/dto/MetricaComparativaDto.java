package com.CaioRian.AvaliacoesFisicas.models.dto;

public record MetricaComparativaDto(
        String nome,
        String valorData1,
        String valorData2,
        String evolucao,
        String indicador
) {
}
