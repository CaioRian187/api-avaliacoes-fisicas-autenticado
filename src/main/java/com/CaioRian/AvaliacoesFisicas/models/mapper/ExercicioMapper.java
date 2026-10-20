package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.entities.Exercicio;

public abstract class ExercicioMapper {

    public static ExercicioResponseDTO toDtoFromEntity(Exercicio exercicio) {
        return new ExercicioResponseDTO(
                exercicio.getId(),
                exercicio.getNome(),
                exercicio.getDescricao(),
                exercicio.getGrupoMuscular(),
                exercicio.getUrlVideo()
        );
    }

    public static Exercicio toEntityFromDto(ExercicioRequestDTO dto) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome(dto.nome());
        exercicio.setDescricao(dto.descricao());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
        exercicio.setUrlVideo(dto.urlVideo());
        return exercicio;
    }
}
