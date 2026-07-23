package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;

public abstract class CircunferenciaMapper {

    public static CircunferenciaResponseDto toDtoFromEntity(Circunferencias circunferencias){
        return new CircunferenciaResponseDto(
                circunferencias.getId(),
                circunferencias.getData(),
                circunferencias.getAltura(),
                circunferencias.getPeso(),
                circunferencias.getImc(),
                circunferencias.getOmbro(),
                circunferencias.getCintura(),
                circunferencias.getQuadril(),
                circunferencias.getPeitoral(),
                circunferencias.getAbdommen(),
                circunferencias.getCoxaProximalEsquerda(),
                circunferencias.getCoxaProximalDireita(),
                circunferencias.getCoxaMedialEsquerda(),
                circunferencias.getCoxaMedialDireita(),
                circunferencias.getCoxaDistalEsquerda(),
                circunferencias.getCoxaDistalDireita(),
                circunferencias.getPanturrilhaEsquerda(),
                circunferencias.getPanturrilhaDireita(),
                circunferencias.getBracoRelaxadoEsquerdo(),
                circunferencias.getBracoRelaxadoDireito(),
                circunferencias.getBracoContraidoEsquerdo(),
                circunferencias.getBracoContraidoDireito(),
                circunferencias.getAntebraçoEsquerdo(),
                circunferencias.getAntebraçoDireito(),
                new UserProjection(
                        circunferencias.getAluno().getId(),
                        circunferencias.getAluno().getNome(),
                        circunferencias.getAluno().getIdade(),
                        circunferencias.getAluno().getSexo()
                )
        );
    }

    public static Circunferencias toEntityFromDto(CircunferenciaRequestDto dto, User user, Double imc){
        return new Circunferencias(
                dto.data(),
                dto.altura(),
                dto.peso(),
                imc,
                dto.ombro(),
                dto.cintura(),
                dto.quadril(),
                dto.peitoral(),
                dto.abdommen(),
                dto.coxaProximalEsquerda(),
                dto.coxaProximalDireita(),
                dto.coxaMedialEsquerda(),
                dto.coxaMedialDireita(),
                dto.coxaDistalEsquerda(),
                dto.coxaDistalDireita(),
                dto.panturrilhaEsquerda(),
                dto.panturrilhaDireita(),
                dto.bracoRelaxadoEsquerdo(),
                dto.bracoRelaxadoDireito(),
                dto.bracoContraidoEsquerdo(),
                dto.bracoContraidoDireito(),
                dto.antebraçoEsquerdo(),
                dto.antebraçoDireito(),
                user
        );
    }
}
