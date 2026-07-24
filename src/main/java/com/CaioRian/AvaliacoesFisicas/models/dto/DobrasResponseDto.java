package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.entities.DobrasCutaneas;
import com.CaioRian.AvaliacoesFisicas.models.mapper.UserMappper;
import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;

import java.time.LocalDate;

public record DobrasResponseDto(
        Long id,

        LocalDate data,

        Double biceps,

        Double peitoral,

        Double triceps,

        Double subescapular,

        Double panturrilhaMedial,

        Double abdominal,

        Double suprailiaca,

        Double coxa,

        Double relacaoCinturaQuadril,

        Double percentualGordura,

        UserProjection aluno
){

}
