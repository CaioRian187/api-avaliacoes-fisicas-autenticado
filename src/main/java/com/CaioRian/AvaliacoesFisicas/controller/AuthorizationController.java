package com.CaioRian.AvaliacoesFisicas.controller;

import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.LoginRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.CadastroRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.LoginResponseDto;
import com.CaioRian.AvaliacoesFisicas.security.TokenService;
import com.CaioRian.AvaliacoesFisicas.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UserResponseDto> cadastrar(@RequestBody @Valid CadastroRequestDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.cadastrar(dto));
    }
}
