package com.CaioRian.AvaliacoesFisicas.models;

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
@Table(name = "antropometria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Circunferencias {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "data", nullable = false)
    @NotNull
    private LocalDate data;

    @Column(name = "altura", nullable = false)
    @NotNull
    private Double altura;

    @Column(name = "peso", nullable = false)
    @NotNull
    private Double peso;

    @Column(name = "imc", nullable = true)
    private Double imc;

    @Column(name = "ombro", nullable = false)
    @NotNull
    private Double ombro;

    @Column(name = "cintura", nullable = false)
    @NotNull
    private Double cintura;

    @Column(name = "quadril", nullable = false)
    @NotNull
    private Double quadril;

    @Column(name = "peitoral", nullable = false)
    @NotNull
    private Double peitoral;

    @Column(name = "abdomen", nullable = false)
    @NotNull
    private Double abdommen;

    @Column(name = "coxaProximalEsquerda", nullable = false)
    @NotNull
    private Double coxaProximalEsquerda;

    @Column(name = "coxaProximalDireita", nullable = false)
    @NotNull
    private Double coxaProximalDireita;

    @Column(name = "coxaMedialEsquerda", nullable = false)
    @NotNull
    private Double coxaMedialEsquerda;

    @Column(name = "coxaMedialDireita", nullable = false)
    @NotNull
    private Double coxaMedialDireita;

    @Column(name = "coxaDistalEsquerda", nullable = false)
    @NotNull
    private Double coxaDistalEsquerda;

    @Column(name = "coxaDistalDireita", nullable = false)
    @NotNull
    private Double coxaDistalDireita;

    @Column(name = "panturrilhaEsquerda", nullable = false)
    @NotNull
    private Double panturrilhaEsquerda;

    @Column(name = "panturrilhaDireita", nullable = false)
    @NotNull
    private Double panturrilhaDireita;

    @Column(name = "braçoRelaxadoEsquerdo", nullable = false)
    @NotNull
    private Double bracoRelaxadoEsquerdo;

    @Column(name = "braçoRelaxadoDireito", nullable = false)
    @NotNull
    private Double bracoRelaxadoDireito;

    @Column(name = "braçoContraidoEsquerdo", nullable = false)
    @NotNull
    private Double bracoContraidoEsquerdo;

    @Column(name = "braçoContraidoDireito", nullable = false)
    @NotNull
    private Double bracoContraidoDireito;

    @Column(name = "antebraçoEsquerdo", nullable = false)
    @NotNull
    private Double antebraçoEsquerdo;

    @Column(name = "antebraçoDireito", nullable = false)
    @NotNull
    private Double antebraçoDireito;
    
    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private User aluno;

    public Circunferencias(LocalDate data, Double altura, Double peso, Double imc, Double ombro, Double cintura, Double quadril, Double peitoral, Double abdommen, Double coxaProximalEsquerda, Double coxaProximalDireita, Double coxaMedialEsquerda, Double coxaMedialDireita, Double coxaDistalEsquerda, Double coxaDistalDireita, Double panturrilhaEsquerda, Double panturrilhaDireita, Double bracoRelaxadoEsquerdo, Double bracoRelaxadoDireito, Double bracoContraidoEsquerdo, Double bracoContraidoDireito, Double antebraçoEsquerdo, Double antebraçoDireito, User aluno) {
        this.data = data;
        this.altura = altura;
        this.peso = peso;
        this.imc = imc;
        this.ombro = ombro;
        this.cintura = cintura;
        this.quadril = quadril;
        this.peitoral = peitoral;
        this.abdommen = abdommen;
        this.coxaProximalEsquerda = coxaProximalEsquerda;
        this.coxaProximalDireita = coxaProximalDireita;
        this.coxaMedialEsquerda = coxaMedialEsquerda;
        this.coxaMedialDireita = coxaMedialDireita;
        this.coxaDistalEsquerda = coxaDistalEsquerda;
        this.coxaDistalDireita = coxaDistalDireita;
        this.panturrilhaEsquerda = panturrilhaEsquerda;
        this.panturrilhaDireita = panturrilhaDireita;
        this.bracoRelaxadoEsquerdo = bracoRelaxadoEsquerdo;
        this.bracoRelaxadoDireito = bracoRelaxadoDireito;
        this.bracoContraidoEsquerdo = bracoContraidoEsquerdo;
        this.bracoContraidoDireito = bracoContraidoDireito;
        this.antebraçoEsquerdo = antebraçoEsquerdo;
        this.antebraçoDireito = antebraçoDireito;
        this.aluno = aluno;
    }
}
