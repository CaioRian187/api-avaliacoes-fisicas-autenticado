package com.CaioRian.AvaliacoesFisicas.services;

import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoExercicioRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.entities.Exercicio;
import com.CaioRian.AvaliacoesFisicas.models.entities.Treino;
import com.CaioRian.AvaliacoesFisicas.models.entities.TreinoExercicio;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;
import com.CaioRian.AvaliacoesFisicas.models.mapper.TreinoMapper;
import com.CaioRian.AvaliacoesFisicas.repository.TreinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final ExercicioService exercicioService;
    private final UserService userService;

    @Transactional
    public TreinoResponseDTO create(TreinoRequestDTO dto) {
        User aluno = this.userService.findEntityById(dto.alunoId());

        Treino treino = new Treino();
        treino.setNomeTreino(dto.nomeTreino());
        treino.setObservacoes(dto.observacoes());
        treino.setDiaSemana(dto.diaSemana());
        treino.setAluno(aluno);

        for (TreinoExercicioRequestDTO item : dto.exercicios()) {
            Exercicio exercicio = this.exercicioService.findEntityById(item.exercicioId());

            TreinoExercicio treinoExercicio = new TreinoExercicio();
            treinoExercicio.setTreino(treino);
            treinoExercicio.setExercicio(exercicio);
            treinoExercicio.setQuantidadeSeries(item.quantidadeSeries());
            treinoExercicio.setQuantidadeRepeticoes(item.quantidadeRepeticoes());
            treinoExercicio.setCarga(item.carga());
            treinoExercicio.setTempoDescanso(item.tempoDescanso());

            treino.getExerciciosDoTreino().add(treinoExercicio);
        }

        this.treinoRepository.save(treino);

        return TreinoMapper.toDtoFromEntity(treino);
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> findAllByAlunoId(UUID alunoId, DiaSemanaEnum diaSemana) {
        this.userService.findEntityById(alunoId);

        List<Treino> treinos = diaSemana == null
                ? this.treinoRepository.findAllByAlunoId(alunoId)
                : this.treinoRepository.findAllByAlunoIdAndDiaSemana(alunoId, diaSemana);

        return treinos.stream()
                .map(TreinoMapper::toDtoFromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> findMeusTreinos(DiaSemanaEnum diaSemana) {
        UUID alunoId = getAlunoAutenticado().getId();

        List<Treino> treinos = diaSemana == null
                ? this.treinoRepository.findAllByAlunoId(alunoId)
                : this.treinoRepository.findAllByAlunoIdAndDiaSemana(alunoId, diaSemana);

        return treinos.stream()
                .map(TreinoMapper::toDtoFromEntity)
                .toList();
    }

    @Transactional
    public void delete(UUID id) {
        findEntityById(id);

        try {
            this.treinoRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir, pois a ficha de treino possui vinculações");
        }
    }

    @Transactional(readOnly = true)
    public Treino findEntityById(UUID id) {
        return this.treinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Treino de Id: " + id + " não encontrado"
                ));
    }

    private User getAlunoAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User aluno)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        return aluno;
    }
}
