package com.CaioRian.AvaliacoesFisicas.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dobrasCutaneas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DobrasCutaneas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "data", nullable = false)
    @NotNull
    private LocalDate data;

    @Column(name = "biceps", nullable = false)
    @NotNull
    private Double biceps;

    @Column(name = "peitoral", nullable = false)
    @NotNull
    private Double peitoral;

    @Column(name = "triceps", nullable = false)
    @NotNull
    private Double triceps;

    @Column(name = "subscapular", nullable = false)
    @NotNull
    private Double subescapular;

    @Column(name = "panturrilhaMedial", nullable = false)
    @NotNull
    private Double panturrilhaMedial;

    @Column(name = "abdominal", nullable = false)
    @NotNull
    private Double abdominal;

    @Column(name = "suprailiaca", nullable = false)
    @NotNull
    private Double suprailiaca;

    @Column(name = "coxa", nullable = false)
    @NotNull
    private Double coxa;

    @Column(name = "relacaoCinturaQuadril", nullable = false)
    private Double relacaoCinturaQuadril;

    @Column(name = "percentualGordura", nullable = false)
    private Double percentualGordura;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private User aluno;


    public DobrasCutaneas(LocalDate data, Double biceps, Double peitoral, Double triceps, Double subescapular, Double panturrilhaMedial, Double abdominal, Double suprailiaca, Double coxa, Double relacaoCinturaQuadril, Double percentualGordura, User aluno) {
        this.data = data;
        this.biceps = biceps;
        this.peitoral = peitoral;
        this.triceps = triceps;
        this.subescapular = subescapular;
        this.panturrilhaMedial = panturrilhaMedial;
        this.abdominal = abdominal;
        this.suprailiaca = suprailiaca;
        this.coxa = coxa;
        this.relacaoCinturaQuadril = relacaoCinturaQuadril;
        this.percentualGordura = percentualGordura;
        this.aluno = aluno;
    }
}
