package com.CaioRian.AvaliacoesFisicas.models.entities;

import com.CaioRian.AvaliacoesFisicas.models.enums.GeneroEnum;
import com.CaioRian.AvaliacoesFisicas.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private Integer idade;

    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_deficiencias", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "descricao")
    private List<String> deficiencias = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_lezoes", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "descricao")
    private List<String> lezoes = new ArrayList<>();

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String nome, Integer idade, GeneroEnum genero, List<String> deficiencias, List<String> lezoes,String login, String password, UserRole role) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.login = login;
        this.deficiencias = deficiencias != null ? new ArrayList<>(deficiencias) : new ArrayList<>();
        this.lezoes = lezoes != null ? new ArrayList<>(lezoes) : new ArrayList<>();
        this.password = password;
        this.role = role;
    }

    public User(String nome, Integer idade, GeneroEnum genero, List<String> deficiencias, List<String> lezoes,String login, String password) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.deficiencias = deficiencias != null ? new ArrayList<>(deficiencias) : new ArrayList<>();
        this.lezoes = lezoes != null ? new ArrayList<>(lezoes) : new ArrayList<>();
        this.login = login;
        this.password = password;
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a autoridade baseada na String da Role
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
