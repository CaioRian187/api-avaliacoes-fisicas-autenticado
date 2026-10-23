package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroRequestDto(
        @NotBlank(message = "O nome não pode ser nulo ou vazio.")
        String nome,

        @NotNull(message = "A idade não pode ser nula.")
        Integer idade,

        @NotNull(message = "O gênero não pode ser nulo.")
        GeneroEnum genero,

        @NotBlank(message = "O login não pode ser nulo ou vazio.")
        String login,

        @NotBlank(message = "A senha não pode ser nula ou vazia.")
        String password

    ) {
}
