package com.CaioRian.AvaliacoesFisicas.models.mapper;

import com.CaioRian.AvaliacoesFisicas.models.dto.CadastroRequestDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserMappperTest {

    @Test
    void fromDtoToEntityDeveMapearDeficienciasELezoes() {
        CadastroRequestDto dto = new CadastroRequestDto(
                "Ana",
                28,
                GeneroEnum.MULHER_CISGENERO,
                "ana",
                List.of("Deficiência visual", "Deficiência auditiva"),
                List.of("Lesão no joelho"),
                "senha-segura"
        );

        User user = UserMappper.fromDtoToEntity(dto);

        assertEquals("Ana", user.getNome());
        assertEquals(28, user.getIdade());
        assertEquals(GeneroEnum.MULHER_CISGENERO, user.getGenero());
        assertEquals("ana", user.getLogin());
        assertEquals("senha-segura", user.getPassword());
        assertEquals(List.of("Deficiência visual", "Deficiência auditiva"), user.getDeficiencias());
        assertEquals(List.of("Lesão no joelho"), user.getLezoes());
    }

    @Test
    void fromDtoToEntityDeveInicializarListasQuandoNulas() {
        CadastroRequestDto dto = new CadastroRequestDto(
                "Bruno",
                35,
                GeneroEnum.HOMEM_CISGENERO,
                "bruno",
                null,
                null,
                "senha"
        );

        User user = UserMappper.fromDtoToEntity(dto);

        assertNotNull(user.getDeficiencias());
        assertNotNull(user.getLezoes());
        assertTrue(user.getDeficiencias().isEmpty());
        assertTrue(user.getLezoes().isEmpty());
    }

    @Test
    void fromEntityToDtoDeveMapearDeficienciasELezoes() {
        User user = new User(
                "Carla",
                42,
                GeneroEnum.MULHER_TRANSGENERO,
                List.of("Deficiência física"),
                List.of("Lesão no ombro", "Lesão no tornozelo"),
                "carla",
                "senha",
                UserRole.ADMIN
        );
        user.setId(UUID.randomUUID());

        UserResponseDto dto = UserMappper.fromEntityToDto(user);

        assertEquals(user.getId(), dto.id());
        assertEquals("Carla", dto.nome());
        assertEquals(42, dto.idade());
        assertEquals(GeneroEnum.MULHER_TRANSGENERO, dto.genero());
        assertEquals(List.of("Deficiência física"), dto.deficiencias());
        assertEquals(List.of("Lesão no ombro", "Lesão no tornozelo"), dto.lezoes());
        assertEquals("carla", dto.login());
        assertEquals("senha", dto.password());
        assertEquals(UserRole.ADMIN, dto.role());
    }
}
