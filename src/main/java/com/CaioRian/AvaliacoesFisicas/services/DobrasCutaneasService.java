package com.CaioRian.AvaliacoesFisicas.services;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.mapper.DobrasCutaneasMapper;
import com.CaioRian.AvaliacoesFisicas.repository.CircunferenciasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import com.CaioRian.AvaliacoesFisicas.models.entities.DobrasCutaneas;
import com.CaioRian.AvaliacoesFisicas.repository.DobrasCutaneasRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DobrasCutaneasService {
    
    private final DobrasCutaneasRepository dobrasCutaneasRepository;
    private final CircunferenciasRepository circunferenciasRepository;
    private final UserService userService;
    
    public DobrasResponseDto findById(Long id){
        DobrasCutaneas dobras = this.dobrasCutaneasRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dobras de Id: " + id + " não encontradas.")
                );

        return DobrasCutaneasMapper.toDtoFromEntity(dobras);
    }

    public List<DobrasResponseDto> findAllByAlunoId(UUID id){
        List<DobrasResponseDto> list = this.dobrasCutaneasRepository.findByAluno_id(id)
                .stream()
                .map(DobrasCutaneasMapper::toDtoFromEntity).toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Dobras Cutâneas não encontradas.");
        }
        return list;
    }

    public List<DobrasResponseDto> findAll(){
        List<DobrasResponseDto> list = this.dobrasCutaneasRepository.findAll()
                .stream()
                .map(DobrasCutaneasMapper::toDtoFromEntity).toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Dobras Cutâneas não encontradas.");
        }
        return list;
    }

    @Transactional
    public DobrasResponseDto createDobras(DobrasRequestDto dto) {

        User aluno = this.userService.findEntityById(dto.aluno_id());

        Circunferencias circ = obterUltimaCircunferencia(dto.aluno_id());
        double rcqFormatado = calcularRelacaoCinturaQuadril(circ);
        double percentualGorduraFormatado = calcularPercentualGordura(aluno, dto);

        DobrasCutaneas dobras = DobrasCutaneasMapper.toEntityFromDto(dto, aluno, rcqFormatado, percentualGorduraFormatado);

        this.dobrasCutaneasRepository.save(dobras);

        return DobrasCutaneasMapper.toDtoFromEntity(dobras);
    }

    @Transactional
    public DobrasResponseDto updateDobras(Long id, DobrasRequestDto dto) {

        User aluno = this.userService.findEntityById(dto.aluno_id());

        DobrasCutaneas dobras = this.dobrasCutaneasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Registro de dobras cutâneas não encontrado."));

        Circunferencias circ = obterUltimaCircunferencia(aluno.getId());

        dobras.setData(dto.data());
        dobras.setBiceps(dto.biceps());
        dobras.setTriceps(dto.triceps());
        dobras.setPeitoral(dto.peitoral());
        dobras.setSubescapular(dto.subescapular());
        dobras.setPanturrilhaMedial(dto.panturrilhaMedial());
        dobras.setAbdominal(dto.abdominal());
        dobras.setSuprailiaca(dto.suprailiaca());
        dobras.setCoxa(dto.coxa());

        dobras.setRelacaoCinturaQuadril(calcularRelacaoCinturaQuadril(circ));
        dobras.setPercentualGordura(calcularPercentualGordura(aluno, dto));

        this.dobrasCutaneasRepository.save(dobras);
        return DobrasCutaneasMapper.toDtoFromEntity(dobras);
    }

    public void deletarDobras(Long id){
        findById(id);

        try{
            this.dobrasCutaneasRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException("Não é possível excluir, pois há vinculações");
        }
    }

    private Circunferencias obterUltimaCircunferencia(UUID alunoId) {
        List<Circunferencias> lista = this.circunferenciasRepository.findAllByAlunoId(alunoId);

        if (lista.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "O aluno precisa ter circunferências cadastradas para calcular as dobras.");
        }

        return lista.get(lista.size() - 1);
    }

    private double calcularRelacaoCinturaQuadril(Circunferencias circ) {
        Double cintura = circ.getCintura();
        Double quadril = circ.getQuadril();

        if (cintura == null || quadril == null || quadril <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Cintura e quadril devem estar preenchidos e o quadril deve ser maior que zero.");
        }

        double rcqBruto = cintura / quadril;
        return Math.round(rcqBruto * 100.0) / 100.0;
    }

    private double calcularPercentualGordura(User aluno, DobrasRequestDto dto) {
        GeneroEnum genero = aluno.getGenero();

        if (genero == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O gênero do aluno é obrigatório para o cálculo antropométrico.");
        }

        GeneroEnum.CategoriaAntropometrica categoria = genero.getCategoriaAntropometrica();

        if (categoria == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O gênero " + genero + " não possui equação antropométrica definida.");
        }

        if (aluno.getIdade() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A idade do aluno é obrigatória para o cálculo antropométrico.");
        }

        int idade = aluno.getIdade();
        double densidade;

        if (categoria == GeneroEnum.CategoriaAntropometrica.MASCULINO) {
            double somatorio = dto.peitoral() + dto.abdominal() + dto.coxa();
            densidade = 1.109380 - (0.0008267 * somatorio) + (0.0000016 * Math.pow(somatorio, 2)) - (0.0002574 * idade);
        } else {
            double somatorio = dto.triceps() + dto.suprailiaca() + dto.coxa();
            densidade = 1.099421 - (0.0009929 * somatorio) + (0.0000023 * Math.pow(somatorio, 2)) - (0.0001392 * idade);
        }

        if (!Double.isFinite(densidade) || densidade <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Não foi possível calcular a densidade corporal com os valores informados.");
        }

        double percentual = ((4.95 / densidade) - 4.50) * 100;
        return Math.round(percentual * 100.0) / 100.0;
    }
}
