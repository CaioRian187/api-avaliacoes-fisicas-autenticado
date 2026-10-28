package com.CaioRian.AvaliacoesFisicas.services;

import com.CaioRian.AvaliacoesFisicas.models.dto.CadastroRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserPutRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;
import com.CaioRian.AvaliacoesFisicas.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void cadastrarDevePersistirDeficienciasELezoesComSenhaCriptografadaERoleUser() {
        CadastroRequestDto dto = new CadastroRequestDto(
                "Ana",
                28,
                GeneroEnum.MULHER_CISGENERO,
                "ana",
                List.of("Deficiência visual"),
                List.of("Lesão no joelho"),
                "senha-segura"
        );
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.cadastrar(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Ana", savedUser.getNome());
        assertEquals(List.of("Deficiência visual"), savedUser.getDeficiencias());
        assertEquals(List.of("Lesão no joelho"), savedUser.getLezoes());
        assertEquals(UserRole.USER, savedUser.getRole());
        assertNotEquals("senha-segura", savedUser.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("senha-segura", savedUser.getPassword()));

        assertEquals("Ana", response.nome());
        assertEquals(List.of("Deficiência visual"), response.deficiencias());
        assertEquals(List.of("Lesão no joelho"), response.lezoes());
        assertEquals(UserRole.USER, response.role());
    }

    @Test
    void cadastrarDeveAceitarListasNulasSemQuebrar() {
        CadastroRequestDto dto = new CadastroRequestDto(
                "Bruno",
                35,
                GeneroEnum.HOMEM_CISGENERO,
                "bruno",
                null,
                null,
                "senha"
        );
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.cadastrar(dto);

        assertTrue(response.deficiencias().isEmpty());
        assertTrue(response.lezoes().isEmpty());
    }

    @Test
    void updateDeveSubstituirDeficienciasELezoesQuandoInformadas() {
        UUID id = UUID.randomUUID();
        User user = criarUsuario(id, List.of("Deficiência antiga"), List.of("Lesão antiga"));
        UserPutRequestDto dto = new UserPutRequestDto(
                "Nome atualizado",
                31,
                List.of("Deficiência nova"),
                List.of("Lesão nova", "Lesão nova 2"),
                GeneroEnum.HOMEM_TRANSGENERO
        );
        when(userRepository.findEntityById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.update(id, dto);

        assertEquals("Nome atualizado", user.getNome());
        assertEquals(31, user.getIdade());
        assertEquals(GeneroEnum.HOMEM_TRANSGENERO, user.getGenero());
        assertEquals(List.of("Deficiência nova"), user.getDeficiencias());
        assertEquals(List.of("Lesão nova", "Lesão nova 2"), user.getLezoes());
        assertEquals(List.of("Deficiência nova"), response.deficiencias());
        assertEquals(List.of("Lesão nova", "Lesão nova 2"), response.lezoes());
        verify(userRepository).save(user);
    }

    @Test
    void updateNaoDeveSobrescreverListasComNull() {
        UUID id = UUID.randomUUID();
        User user = criarUsuario(id, List.of("Deficiência existente"), List.of("Lesão existente"));
        UserPutRequestDto dto = new UserPutRequestDto(
                "Nome atualizado",
                33,
                null,
                null,
                GeneroEnum.GENERO_NAO_BINARIO
        );
        when(userRepository.findEntityById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.update(id, dto);

        assertEquals(List.of("Deficiência existente"), user.getDeficiencias());
        assertEquals(List.of("Lesão existente"), user.getLezoes());
        assertEquals(List.of("Deficiência existente"), response.deficiencias());
        assertEquals(List.of("Lesão existente"), response.lezoes());
    }

    @Test
    void updateDevePermitirLimparListasComListasVazias() {
        UUID id = UUID.randomUUID();
        User user = criarUsuario(id, List.of("Deficiência existente"), List.of("Lesão existente"));
        UserPutRequestDto dto = new UserPutRequestDto(
                "Nome atualizado",
                33,
                List.of(),
                List.of(),
                GeneroEnum.GENERO_NAO_BINARIO
        );
        when(userRepository.findEntityById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userService.update(id, dto);

        assertTrue(user.getDeficiencias().isEmpty());
        assertTrue(user.getLezoes().isEmpty());
        assertTrue(response.deficiencias().isEmpty());
        assertTrue(response.lezoes().isEmpty());
    }

    @Test
    void findByIdDeveRetornarDeficienciasELezoes() {
        UUID id = UUID.randomUUID();
        User user = criarUsuario(id, List.of("Deficiência física"), List.of("Lesão lombar"));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponseDto response = userService.findById(id);

        assertEquals(id, response.id());
        assertEquals(List.of("Deficiência física"), response.deficiencias());
        assertEquals(List.of("Lesão lombar"), response.lezoes());
        assertFalse(response.deficiencias().isEmpty());
    }

    private User criarUsuario(UUID id, List<String> deficiencias, List<String> lezoes) {
        User user = new User(
                "Usuário Original",
                20,
                GeneroEnum.HOMEM_CISGENERO,
                deficiencias,
                lezoes,
                "usuario",
                "senha",
                UserRole.USER
        );
        user.setId(id);
        return user;
    }
}
