package com.CaioRian.AvaliacoesFisicas.repository;

import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.models.dto.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{

    List<UserResponseDto> findByNomeContainingIgnoreCase(String nome);

    org.springframework.security.core.userdetails.UserDetails findByLogin(String login);

    @Query("select u from users u where id = :id")
    Optional<User> findEntityById(UUID id);
}
