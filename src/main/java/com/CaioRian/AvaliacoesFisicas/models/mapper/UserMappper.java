package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.CadastroRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;

public abstract class UserMappper {

    public static UserResponseDto fromEntityToDto(User user){
        return new UserResponseDto(
                user.getId(),
                user.getNome(),
                user.getIdade(),
                user.getGenero(),
                user.getDeficiencias(),
                user.getLezoes(),
                user.getRole()
        );
    }

    public static User fromDtoToEntity(CadastroRequestDto dto){
        return new User(
                dto.nome(),
                dto.idade(),
                dto.genero(),
                dto.deficiencias(),
                dto.lezoes(),
                dto.login(),
                dto.password()
        );
    }
}
