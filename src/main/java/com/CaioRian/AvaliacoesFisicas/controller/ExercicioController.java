package com.CaioRian.AvaliacoesFisicas.controller;

import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.ExercicioResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.enums.GrupoMuscularEnum;
import com.CaioRian.AvaliacoesFisicas.services.ExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Exercícios")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/exercicios")
@Validated
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService exercicioService;

    @Operation(summary = "Cadastra um novo exercício no catálogo (ADMIN)")
    @PostMapping
    public ResponseEntity<ExercicioResponseDTO> create(@RequestBody @Valid ExercicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.exercicioService.create(dto));
    }

    @Operation(summary = "Lista todos os exercícios, com filtro opcional por grupo muscular")
    @GetMapping
    public ResponseEntity<List<ExercicioResponseDTO>> findAll(
            @RequestParam(required = false) GrupoMuscularEnum grupoMuscular) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.exercicioService.findAll(grupoMuscular));
    }

    @Operation(summary = "Busca um exercício por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ExercicioResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.exercicioService.findById(id));
    }

    @Operation(summary = "Atualiza um exercício existente (ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<ExercicioResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid ExercicioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.exercicioService.update(id, dto));
    }

    @Operation(summary = "Remove um exercício do catálogo (ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.exercicioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
