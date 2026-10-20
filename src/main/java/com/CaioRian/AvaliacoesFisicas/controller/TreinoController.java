package com.CaioRian.AvaliacoesFisicas.controller;

import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoRequestDTO;
import com.CaioRian.AvaliacoesFisicas.models.dto.TreinoResponseDTO;
import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;
import com.CaioRian.AvaliacoesFisicas.services.TreinoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Treinos")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/treinos")
@Validated
@RequiredArgsConstructor
public class TreinoController {

    private final TreinoService treinoService;

    @PostMapping
    public ResponseEntity<TreinoResponseDTO> create(@RequestBody @Valid TreinoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.treinoService.create(dto));
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<TreinoResponseDTO>> findAllByAlunoId(
            @PathVariable UUID alunoId,
            @RequestParam(required = false) DiaSemanaEnum diaSemana) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.treinoService.findAllByAlunoId(alunoId, diaSemana));
    }

    @GetMapping("/meus-treinos")
    public ResponseEntity<List<TreinoResponseDTO>> findMeusTreinos(
            @RequestParam(required = false) DiaSemanaEnum diaSemana) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.treinoService.findMeusTreinos(diaSemana));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.treinoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
