package com.CaioRian.AvaliacoesFisicas.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserPutRequestDto(

    @NotBlank(message = "O nome não pode ser nulo ou vazio.")
    String nome,

    @NotNull(message = "A idade não pode ser nula.")
    Integer idade,

    @NotBlank(message = "O sexo não pode ser nulo ou vazio.")
    String sexo
){
    
}
