package com.CaioRian.AvaliacoesFisicas.controller;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.DobrasResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.CaioRian.AvaliacoesFisicas.services.DobrasCutaneasService;

import jakarta.validation.Valid;

@Tag(name = "Dobras Cutâneas")
@CrossOrigin("*")
@RestController
@RequestMapping("/dobrasCutaneas")
@Validated
public class DobrasCutaneasController {
    
    @Autowired
    private DobrasCutaneasService dobrasCutaneasService;

    @GetMapping("/{id}")
    public ResponseEntity<DobrasResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.dobrasCutaneasService.findById(id));
    }

    @GetMapping("aluno/{id_aluno}")
    public ResponseEntity<List<DobrasResponseDto>> findAllByAlunoId(@PathVariable UUID id_aluno){
        return ResponseEntity.ok().body(this.dobrasCutaneasService.findAllByAlunoId(id_aluno));
    }

    @GetMapping
    public ResponseEntity<List<DobrasResponseDto>> findAll(){
        return ResponseEntity.ok().body(this.dobrasCutaneasService.findAll());
    }

    @PostMapping()
    public ResponseEntity<DobrasResponseDto> create(@RequestBody @Valid DobrasRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.dobrasCutaneasService.createDobras(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DobrasResponseDto> update(@PathVariable Long id, @RequestBody @Valid DobrasRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(this.dobrasCutaneasService.updateDobras(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.dobrasCutaneasService.deletarDobras(id);
        return ResponseEntity.noContent().build();
    }
    
}
