package com.CaioRian.AvaliacoesFisicas.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CaioRian.AvaliacoesFisicas.models.dto.PutRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.services.UserService;

import jakarta.validation.Valid;

@Tag(name = "Usuários")
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

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid PutRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.userService.deleteAluno(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
