package com.CaioRian.AvaliacoesFisicas.models.dto;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserPutRequestDto(

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    String nome,

    @NotNull(message = "A idade não pode ser nula.")
    Integer idade,

    @NotNull(message = "O gênero não pode ser nulo.")
    GeneroEnum genero
){
    
}
