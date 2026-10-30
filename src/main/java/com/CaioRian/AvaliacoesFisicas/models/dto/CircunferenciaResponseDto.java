package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;

import java.time.LocalDate;
import java.util.UUID;

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

        Double abdomen,

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
    public CircunferenciaResponseDto(
            Long id, LocalDate data, Double altura, Double peso, Double imc, Double ombro,
            Double cintura, Double quadril, Double peitoral, Double abdomen, Double coxaProximalEsquerda,
            Double coxaProximalDireita, Double coxaMedialEsquerda, Double coxaMedialDireita,
            Double coxaDistalEsquerda, Double coxaDistalDireita, Double panturrilhaEsquerda,
            Double panturrilhaDireita, Double bracoRelaxadoEsquerdo, Double bracoRelaxadoDireito,
            Double bracoContraidoEsquerdo, Double bracoContraidoDireito, Double antebraçoEsquerdo,
            Double antebraçoDireito, UUID alunoId, String nomeAluno, Integer idadeAluno, GeneroEnum generoAluno
    ) {
        this(
                id, data, altura, peso, imc, ombro, cintura, quadril, peitoral, abdomen,
                coxaProximalEsquerda, coxaProximalDireita, coxaMedialEsquerda, coxaMedialDireita,
                coxaDistalEsquerda, coxaDistalDireita, panturrilhaEsquerda, panturrilhaDireita,
                bracoRelaxadoEsquerdo, bracoRelaxadoDireito, bracoContraidoEsquerdo, bracoContraidoDireito,
                antebraçoEsquerdo, antebraçoDireito,
                new UserProjection(alunoId, nomeAluno, idadeAluno, generoAluno)
        );
    }
}
