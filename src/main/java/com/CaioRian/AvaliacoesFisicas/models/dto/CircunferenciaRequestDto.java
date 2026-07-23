package com.CaioRian.AvaliacoesFisicas.models.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CircunferenciaRequestDto(

        @NotNull(message = "A data não pode ser nula.")
        LocalDate data,

        @NotNull(message = "A altura não pode ser nula.")
        Double altura,

        @NotNull(message = "O peso não pode ser nulo.")
        Double peso,

        @NotNull(message = "O ombro não pode ser nulo.")
        Double ombro,

        @NotNull(message = "A cintura não pode ser nula.")
        Double cintura,

        @NotNull(message = "O quadril não pode ser nulo.")
        Double quadril,

        @NotNull(message = "O peitoral não pode ser nulo.")
        Double peitoral,

        @NotNull(message = "O abdômen não pode ser nulo")
        Double abdommen,

        @NotNull(message = "A coxa proximal esquerda não pode ser nula.")
        Double coxaProximalEsquerda,

        @NotNull(message = "A coxa proximal direita não pode ser nula.")
        Double coxaProximalDireita,

        @NotNull(message = "A coxa medial esquerda não pode ser nula.")
        Double coxaMedialEsquerda,

        @NotNull(message = "A coxa medial direita não pode ser nula.")
        Double coxaMedialDireita,

        @NotNull(message = "A coxa distal esquerda não pode ser nula.")
        Double coxaDistalEsquerda,

        @NotNull(message = "A coxa distal direita não pode ser nula.")
        Double coxaDistalDireita,

        @NotNull(message = "A panturrilha esquerda não pode ser nula.")
        Double panturrilhaEsquerda,

        @NotNull(message = "A panturrilha direita não pode ser nula.")
        Double panturrilhaDireita,

        @NotNull(message = "O braço relaxado esquerdo não pode ser nulo.")
        Double bracoRelaxadoEsquerdo,

        @NotNull(message = "O braço relaxado direito não pode ser nulo.")
        Double bracoRelaxadoDireito,

        @NotNull(message = "O braço contraído esquerdo não pode ser nulo.")
        Double bracoContraidoEsquerdo,

        @NotNull(message = "O braço contraído direito não pode ser nulo.")
        Double bracoContraidoDireito,

        @NotNull(message = "O antebraço esquerdo não pode ser nulo.")
        Double antebraçoEsquerdo,

        @NotNull(message = "O antebraço direito não pode ser nulo.")
        Double antebraçoDireito,

        @NotNull(message = "O id do aluno não pode ser nulo.")
        UUID aluno_id
) {
}
