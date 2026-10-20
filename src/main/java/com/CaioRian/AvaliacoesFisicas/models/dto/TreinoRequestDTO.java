package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TreinoRequestDTO(

        @NotBlank(message = "O nome do treino não pode ser vazio.")
        String nomeTreino,

        @NotBlank(message = "As observações do treino não podem ser vazias.")
        String observacoes,

        @NotNull(message = "O dia da semana não pode ser nulo.")
        DiaSemanaEnum diaSemana,

        @NotNull(message = "O id do aluno não pode ser nulo.")
        UUID alunoId,

        @NotEmpty(message = "O treino deve possuir pelo menos um exercício.")
        @Valid
        List<TreinoExercicioRequestDTO> exercicios
) {
}
