package com.CaioRian.AvaliacoesFisicas.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TreinoExercicioRequestDTO(

        @NotNull(message = "O id do exercício não pode ser nulo.")
        UUID exercicioId,

        @NotNull(message = "A quantidade de séries não pode ser nula.")
        @Min(value = 1, message = "A quantidade de séries deve ser maior que zero.")
        Integer quantidadeSeries,

        @NotNull(message = "A quantidade de repetições não pode ser nula.")
        @Min(value = 1, message = "A quantidade de repetições deve ser maior que zero.")
        Integer quantidadeRepeticoes,

        String carga,

        @Min(value = 0, message = "O tempo de descanso não pode ser negativo.")
        Integer tempoDescanso
) {
}
