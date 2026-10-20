package com.CaioRian.AvaliacoesFisicas.models.entities;

import com.CaioRian.AvaliacoesFisicas.models.enums.DiaSemanaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "treinos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_treino", nullable = false)
    private String nomeTreino;

    @Column(name = "observações", nullable = false)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemanaEnum diaSemana;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private User aluno;

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreinoExercicio> exerciciosDoTreino = new ArrayList<>();

}
