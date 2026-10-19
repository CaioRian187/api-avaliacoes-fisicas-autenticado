package com.CaioRian.AvaliacoesFisicas.models.dto;

import java.time.LocalDate;
import java.util.List;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;

public record RelatorioComparativoDto(
        User aluno,
        LocalDate dataInicial,
        LocalDate dataFinal,
        String dataInicialFormatada,
        String dataFinalFormatada,
        List<MetricaComparativaDto> circunferencias,
        List<MetricaComparativaDto> dobras
) {
}
