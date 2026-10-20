package com.CaioRian.AvaliacoesFisicas.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "treino_exercicios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreinoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;

    @Column(name = "quantidade_series", nullable = false)
    private Integer quantidadeSeries;

    @Column(name = "quantidade_repeticoes", nullable = false)
    private Integer quantidadeRepeticoes;

    @Column(name = "carga")
    private String carga;

    @Column(name = "tempo_descanso")
    private Integer tempoDescanso;
}
