package com.CaioRian.AvaliacoesFisicas.services;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasResponseDto;
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
    private final CircunferenciasService circunferenciasService;
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

        List<Circunferencias> lista = this.circunferenciasRepository.findAllByAlunoId(dto.aluno_id());
        Circunferencias circ = lista.get(lista.size() - 1);

        double rcqBruto = circ.getCintura() / circ.getQuadril();
        double rcqFormatado = Math.round(rcqBruto * 100.0) / 100.0;

        double somatorioMasculino = dto.peitoral() + dto.abdominal() + dto.coxa();
        double somatorioFeminino = dto.triceps() + dto.suprailiaca() + dto.coxa();

        String sexo = aluno.getSexo();
        int idade = aluno.getIdade();
        double densidade = 0;
        double percentualGorduraFormatado = 0;

        if ("Masculino".equalsIgnoreCase(sexo)) {
            densidade = 1.109380 - (0.0008267 * somatorioMasculino) + (0.0000016 * Math.pow(somatorioMasculino, 2)) - (0.0002574 * idade);
        } else if ("Feminino".equalsIgnoreCase(sexo)) {
            densidade = 1.099421 - (0.0009929 * somatorioFeminino) + (0.0000023 * Math.pow(somatorioFeminino, 2)) - (0.0001392 * idade);
        }

        if (densidade > 0) {
            double percentual = ((4.95 / densidade) - 4.50) * 100;
            percentualGorduraFormatado = Math.round(percentual * 100.0) / 100.0;
        } else {
            percentualGorduraFormatado = 0.0;
        }

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

        List<Circunferencias> listaCirc = this.circunferenciasRepository.findAllByAlunoId(aluno.getId());
        if (listaCirc.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "O aluno precisa ter circunferências cadastradas para atualizar as dobras.");
        }
        Circunferencias circ = listaCirc.get(listaCirc.size() - 1);

        dobras.setData(dto.data());
        dobras.setBiceps(dto.biceps());
        dobras.setTriceps(dto.triceps());
        dobras.setPeitoral(dto.peitoral());
        dobras.setSubescapular(dto.subescapular());
        dobras.setPanturrilhaMedial(dto.panturrilhaMedial());
        dobras.setAbdominal(dto.abdominal());
        dobras.setSuprailiaca(dto.suprailiaca());
        dobras.setCoxa(dto.coxa());

        double rcqBruto = circ.getCintura() / circ.getQuadril();
        dobras.setRelacaoCinturaQuadril(Math.round(rcqBruto * 100.0) / 100.0);

        double somatorioMasculino = dto.peitoral() + dto.abdominal() + dto.coxa();
        double somatorioFeminino = dto.triceps() + dto.suprailiaca() + dto.coxa();

        String sexo = aluno.getSexo();
        int idade = aluno.getIdade();
        double densidade = 0;
        double percentualGorduraFormatado = 0;


        if ("Masculino".equalsIgnoreCase(sexo)) {
            densidade = 1.109380 - (0.0008267 * somatorioMasculino) + (0.0000016 * Math.pow(somatorioMasculino, 2)) - (0.0002574 * idade);
        } else if ("Feminino".equalsIgnoreCase(sexo)) {
            densidade = 1.099421 - (0.0009929 * somatorioFeminino) + (0.0000023 * Math.pow(somatorioFeminino, 2)) - (0.0001392 * idade);
        }

        if (densidade > 0) {
            double percentual = ((4.95 / densidade) - 4.50) * 100;
            dobras.setPercentualGordura(Math.round(percentual * 100.0) / 100.0);
        } else {
            dobras.setPercentualGordura(0.0);
        }

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
}
