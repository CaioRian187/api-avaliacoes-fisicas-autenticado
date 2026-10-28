package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nome,
        Integer idade,
        GeneroEnum genero,
        List<String> deficiencias,
        List<String> lezoes,
        String login,
        String password,
        UserRole role
) {

}
