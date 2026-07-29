package com.CaioRian.AvaliacoesFisicas.controller;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.CircunferenciaResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CaioRian.AvaliacoesFisicas.services.CircunferenciasService;

import jakarta.validation.Valid;

@Tag(name = "Circunferências")
@CrossOrigin("*")
@RestController
@RequestMapping("/circunferencias")
@Validated
@RequiredArgsConstructor
public class CircunferenciasController {
    
    private final CircunferenciasService circunferenciasService;

    @GetMapping("/{id}")
    public ResponseEntity<CircunferenciaResponseDto> findById(@PathVariable Long id){
        CircunferenciaResponseDto circunferencias = this.circunferenciasService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(circunferencias);
    }

    @GetMapping
    public ResponseEntity<List<CircunferenciaResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.circunferenciasService.findAll());
    }

    @GetMapping("aluno/{id_aluno}")
    public ResponseEntity<List<CircunferenciaResponseDto>> findAllByAlunoId(@PathVariable UUID id_aluno){
        return ResponseEntity.status(HttpStatus.OK).body(this.circunferenciasService.findAllByAlunoId(id_aluno));
    }

    @PostMapping
    public ResponseEntity<CircunferenciaResponseDto> createCircunferencia(@RequestBody @Valid CircunferenciaRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.circunferenciasService.createCircunferencia(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CircunferenciaResponseDto> updateCircunferencia(@RequestBody @Valid CircunferenciaRequestDto dto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.circunferenciasService.updateCircunferencias(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCircunferencia(@PathVariable Long id){
        this.circunferenciasService.deletarCircunferencia(id);
        return ResponseEntity.noContent().build();
    }
}
