package com.CaioRian.AvaliacoesFisicas.services;

import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.entities.Exercicio;
import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;
import com.CaioRian.AvaliacoesFisicas.models.mapper.ExercicioMapper;
import com.CaioRian.AvaliacoesFisicas.repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    @Transactional(readOnly = true)
    public List<ExercicioResponseDTO> findAll(GrupoMuscularEnum grupoMuscular) {
        List<Exercicio> exercicios = grupoMuscular == null
                ? this.exercicioRepository.findAll()
                : this.exercicioRepository.findAllByGrupoMuscular(grupoMuscular);

        return exercicios.stream()
                .map(ExercicioMapper::toDtoFromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExercicioResponseDTO findById(UUID id) {
        return ExercicioMapper.toDtoFromEntity(findEntityById(id));
    }

    @Transactional
    public ExercicioResponseDTO create(ExercicioRequestDTO dto) {
        Exercicio exercicio = ExercicioMapper.toEntityFromDto(dto);
        this.exercicioRepository.save(exercicio);
        return ExercicioMapper.toDtoFromEntity(exercicio);
    }

    @Transactional
    public ExercicioResponseDTO update(UUID id, ExercicioRequestDTO dto) {
        Exercicio exercicio = findEntityById(id);

        exercicio.setNome(dto.nome());
        exercicio.setDescricao(dto.descricao());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
        exercicio.setUrlVideo(dto.urlVideo());

        this.exercicioRepository.save(exercicio);

        return ExercicioMapper.toDtoFromEntity(exercicio);
    }

    @Transactional
    public void delete(UUID id) {
        findEntityById(id);

        try {
            this.exercicioRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir, pois o exercício possui vinculações em fichas de treino");
        }
    }

    @Transactional(readOnly = true)
    public Exercicio findEntityById(UUID id) {
        return this.exercicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exercício de Id: " + id + " não encontrado"
                ));
    }
}
