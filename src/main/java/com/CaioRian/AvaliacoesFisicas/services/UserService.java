package com.CaioRian.AvaliacoesFisicas.services;

import java.util.List;

import java.util.UUID;

import com.CaioRian.AvaliacoesFisicas.models.dto.CadastroRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;
import com.CaioRian.AvaliacoesFisicas.models.mapper.UserMappper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserPutRequestDto;
import com.CaioRian.AvaliacoesFisicas.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto cadastrar(CadastroRequestDto dto){

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = UserMappper.fromDtoToEntity(dto);
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.USER);

        this.userRepository.save(newUser);

        return UserMappper.fromEntityToDto(newUser);
    }

    public List<UserResponseDto> findByNomeContainingIgnoreCase(String nome){
        return userRepository.findByNomeContainingIgnoreCase(nome);
    }

    public UserResponseDto findById(UUID id){
        User user = this.userRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário de Id: " + id + " não encontrado"
                ));
        return UserMappper.fromEntityToDto(user);
    }

    public User findEntityById(UUID id){
        return this.userRepository.findEntityById(id)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário de Id: " + id + " não encontrado"
                ));
    }

    public List<UserResponseDto> findAllAlunos(){
        return this.userRepository.findAll()
                .stream()
                .map(UserMappper::fromEntityToDto).toList();
    }

    @Transactional
    public UserResponseDto update(UUID id, UserPutRequestDto aluno){
        User user = this.findEntityById(id);

        user.setNome(aluno.nome());
        user.setIdade(aluno.idade());
        user.setSexo(aluno.sexo());

        this.userRepository.save(user);

        return UserMappper.fromEntityToDto(user);
    }

    public void deleteAluno(UUID id){
        findById(id);
        try{
            this.userRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException("Não é possível excluir, pois o aluno possui vinculações");
        }
    }
    
}
