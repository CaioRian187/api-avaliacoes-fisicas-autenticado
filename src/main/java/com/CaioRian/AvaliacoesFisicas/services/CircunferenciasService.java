package com.CaioRian.AvaliacoesFisicas.services;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.mapper.CircunferenciaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import com.CaioRian.AvaliacoesFisicas.repository.CircunferenciasRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CircunferenciasService {

    private final UserService userService;
    private final CircunferenciasRepository circunferenciasRepository;

    public CircunferenciaResponseDto findById(Long id) {
        Circunferencias circunferencia = this.circunferenciasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Circunferência de Id: " + id + " não encontrada")
                );

        return CircunferenciaMapper.toDtoFromEntity(circunferencia);
    }

    public List<CircunferenciaResponseDto> findAll(){
        List<CircunferenciaResponseDto> list = this.circunferenciasRepository.findAll()
                .stream()
                .map(CircunferenciaMapper::toDtoFromEntity).toList();

        if (list.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nehuma circunferência encontrada"
            );
        }
        return list;
    }

    public List<CircunferenciaResponseDto> findAllByAlunoId(UUID alunoId){
        return this.circunferenciasRepository.findAllByAlunoId(alunoId)
                .stream().map(CircunferenciaMapper::toDtoFromEntity).toList();
    }

    @Transactional
    public CircunferenciaResponseDto createCircunferencia(CircunferenciaRequestDto dto) {
        User user = this.userService.findEntityById(dto.aluno_id());

        double alturaMetros = dto.altura() / 100.0;
        double imc = dto.peso() / (alturaMetros * alturaMetros);
        double imcFormatado = Math.round(imc * 100.0) / 100.0;

        Circunferencias circunferencia = CircunferenciaMapper.toEntityFromDto(dto, user, imcFormatado);

        this.circunferenciasRepository.save(circunferencia);

        return CircunferenciaMapper.toDtoFromEntity(circunferencia);
    }

    @Transactional
    public CircunferenciaResponseDto updateCircunferencias(CircunferenciaRequestDto dto, Long id){
        Circunferencias newCircunferencias = this.circunferenciasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Circunferência não encontrada.")
                );

        newCircunferencias.setData(dto.data());
        newCircunferencias.setAltura(dto.altura());
        newCircunferencias.setPeso(dto.peso());

        double alturaMetros = dto.altura() / 100.0;
        double imc = dto.peso() / (alturaMetros * alturaMetros);
        double imcFormatado = Math.round(imc * 100.0) / 100.0;
        newCircunferencias.setImc(imcFormatado);

        newCircunferencias.setOmbro(dto.ombro());
        newCircunferencias.setCintura(dto.cintura());
        newCircunferencias.setQuadril(dto.quadril());
        newCircunferencias.setPeitoral(dto.peitoral());
        newCircunferencias.setAbdommen(dto.abdommen());
        newCircunferencias.setCoxaProximalEsquerda(dto.coxaProximalEsquerda());
        newCircunferencias.setCoxaProximalDireita(dto.coxaProximalDireita());
        newCircunferencias.setCoxaMedialEsquerda(dto.coxaMedialEsquerda());
        newCircunferencias.setCoxaMedialDireita(dto.coxaMedialDireita());
        newCircunferencias.setCoxaDistalEsquerda(dto.coxaDistalEsquerda());
        newCircunferencias.setCoxaDistalDireita(dto.coxaDistalDireita());
        newCircunferencias.setPanturrilhaEsquerda(dto.panturrilhaEsquerda());
        newCircunferencias.setPanturrilhaDireita(dto.panturrilhaDireita());
        newCircunferencias.setBracoRelaxadoEsquerdo(dto.bracoRelaxadoEsquerdo());
        newCircunferencias.setBracoRelaxadoDireito(dto.bracoRelaxadoDireito());
        newCircunferencias.setBracoContraidoEsquerdo(dto.bracoContraidoEsquerdo());
        newCircunferencias.setBracoContraidoDireito(dto.bracoContraidoDireito());
        newCircunferencias.setAntebraçoEsquerdo(dto.antebraçoEsquerdo());
        newCircunferencias.setAntebraçoDireito(dto.antebraçoDireito());

        this.circunferenciasRepository.save(newCircunferencias);

        return CircunferenciaMapper.toDtoFromEntity(newCircunferencias);
    }

    public void deletarCircunferencia(Long id){
        findById(id);

        try{
            this.circunferenciasRepository.deleteById(id);
        }
        catch(DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException("Não é possível excluir, pois as circunferências possuim vinculações");
        }
    }
}
