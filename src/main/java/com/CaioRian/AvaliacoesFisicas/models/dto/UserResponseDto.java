package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nome,
        Integer idade,
        String sexo,
        String login,
        String password,
        UserRole role
) {

}
