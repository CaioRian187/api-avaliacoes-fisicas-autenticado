package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.DobrasCutaneas;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;

public abstract class DobrasCutaneasMapper {

    public static DobrasResponseDto toDtoFromEntity(DobrasCutaneas dobrasCutaneas){
        return new DobrasResponseDto(
                dobrasCutaneas.getId(),
                dobrasCutaneas.getData(),
                dobrasCutaneas.getBiceps(),
                dobrasCutaneas.getPeitoral(),
                dobrasCutaneas.getTriceps(),
                dobrasCutaneas.getSubescapular(),
                dobrasCutaneas.getPanturrilhaMedial(),
                dobrasCutaneas.getAbdominal(),
                dobrasCutaneas.getSuprailiaca(),
                dobrasCutaneas.getCoxa(),
                dobrasCutaneas.getRelacaoCinturaQuadril(),
                dobrasCutaneas.getPercentualGordura(),
                new UserProjection(
                        dobrasCutaneas.getAluno().getId(),
                        dobrasCutaneas.getAluno().getNome(),
                        dobrasCutaneas.getAluno().getIdade(),
                        dobrasCutaneas.getAluno().getSexo()
                )

        );
    }

    public static DobrasCutaneas toEntityFromDto(DobrasRequestDto dto, User user, Double relacaoCinturaQuadril, Double percentualGordura){
        return new DobrasCutaneas(
                dto.data(),
                dto.biceps(),
                dto.peitoral(),
                dto.triceps(),
                dto.subescapular(),
                dto.panturrilhaMedial(),
                dto.abdominal(),
                dto.suprailiaca(),
                dto.coxa(),
                relacaoCinturaQuadril,
                percentualGordura,
                user
        );
    }
}
