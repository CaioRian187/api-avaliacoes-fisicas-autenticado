package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import com.CaioRian.AvaliacoesFisicas.models.mapper.UserMappper;
import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;

import java.time.LocalDate;

public record CircunferenciaResponseDto(
        Long id,

        LocalDate data,

        Double altura,

        Double peso,

        Double imc,

        Double ombro,

        Double cintura,

        Double quadril,

        Double peitoral,

        Double abdommen,

        Double coxaProximalEsquerda,

        Double coxaProximalDireita,

        Double coxaMedialEsquerda,

        Double coxaMedialDireita,

        Double coxaDistalEsquerda,

        Double coxaDistalDireita,

        Double panturrilhaEsquerda,

        Double panturrilhaDireita,

        Double bracoRelaxadoEsquerdo,

        Double bracoRelaxadoDireito,

        Double bracoContraidoEsquerdo,

        Double bracoContraidoDireito,

        Double antebraçoEsquerdo,

        Double antebraçoDireito,

        UserProjection aluno

) {

}
