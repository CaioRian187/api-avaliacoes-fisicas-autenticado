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
                user.getSexo(),
                user.getLogin(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static User fromDtoToEntity(CadastroRequestDto dto){
        return new User(
                dto.nome(),
                dto.idade(),
                dto.sexo(),
                dto.login(),
                dto.password()
        );
    }
}
