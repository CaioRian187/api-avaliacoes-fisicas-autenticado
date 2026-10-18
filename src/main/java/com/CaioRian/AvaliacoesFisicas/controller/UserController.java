package com.CaioRian.AvaliacoesFisicas.controller;

import java.util.List;
import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.projections.UserProjection;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.CaioRian.AvaliacoesFisicas.models.dto.UserPutRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.services.UserService;

import jakarta.validation.Valid;

@Tag(name = "Usuários")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UserResponseDto>> findByNome(@PathVariable String nome){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findById(id));
    }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAllAlunos());
    }

    @GetMapping("/listar-usuarios-projetions")
    public ResponseEntity<List<UserProjection>> findAllUserProjetion(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAllUserProjetion());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UserPutRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.userService.deleteAluno(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
