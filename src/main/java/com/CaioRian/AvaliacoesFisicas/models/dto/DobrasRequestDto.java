package com.CaioRian.AvaliacoesFisicas.models.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record DobrasRequestDto(

        @NotNull(message = "A data não pode ser nula.")
        LocalDate data,

        @NotNull(message = "O biceps não pode ser nulo.")
        Double biceps,

        @NotNull(message = "O peitoral não pode ser nulo.")
        Double peitoral,

        @NotNull(message = "O triceps não pode ser nulo.")
        Double triceps,

        @NotNull(message = "A subescapular não pode ser nula.")
        Double subescapular,

        @NotNull(message = "A panturrilha medial não pode ser nula.")
        Double panturrilhaMedial,

        @NotNull(message = "O abdômen não pode ser nula.")
        Double abdominal,

        @NotNull(message = "A suprailiaca não pode ser nula.")
        Double suprailiaca,

        @NotNull(message = "A coxa não pode ser nula.")
        Double coxa,

        @NotNull(message = "O id do aluno não pode ser nulo.")
        UUID aluno_id
) {
}
