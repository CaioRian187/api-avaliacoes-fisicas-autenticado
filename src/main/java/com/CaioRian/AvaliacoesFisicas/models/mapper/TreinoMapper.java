package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoExercicioResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.entities.Treino;
import com.CaioRian.AvaliacoesFisicas.models.entities.TreinoExercicio;

import java.util.List;

public abstract class TreinoMapper {

    public static TreinoResponseDTO toDtoFromEntity(Treino treino) {
        List<TreinoExercicioResponseDTO> exercicios = treino.getExerciciosDoTreino()
                .stream()
                .map(TreinoMapper::toDtoFromTreinoExercicio)
                .toList();

        return new TreinoResponseDTO(
                treino.getId(),
                treino.getNomeTreino(),
                treino.getObservacoes(),
                treino.getDiaSemana(),
                treino.getAluno().getId(),
                treino.getAluno().getNome(),
                exercicios
        );
    }

    private static TreinoExercicioResponseDTO toDtoFromTreinoExercicio(TreinoExercicio treinoExercicio) {
        return new TreinoExercicioResponseDTO(
                treinoExercicio.getId(),
                treinoExercicio.getExercicio().getId(),
                treinoExercicio.getExercicio().getNome(),
                treinoExercicio.getExercicio().getGrupoMuscular(),
                treinoExercicio.getQuantidadeSeries(),
                treinoExercicio.getQuantidadeRepeticoes(),
                treinoExercicio.getCarga(),
                treinoExercicio.getTempoDescanso()
        );
    }
}
