package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExercicioRequestDTO(

        @NotBlank(message = "O nome do exercício não pode ser vazio.")
        String nome,

        @NotBlank(message = "A descrição do exercício não pode ser vazia.")
        String descricao,

        @NotNull(message = "O grupo muscular não pode ser nulo.")
        GrupoMuscularEnum grupoMuscular,

        @NotBlank(message = "A URL do vídeo não pode ser vazia.")
        String urlVideo
) {
}
