package com.CaioRian.AvaliacoesFisicas.models.enums;

public enum GeneroEnum {
    HOMEM_CISGENERO(CategoriaAntropometrica.MASCULINO),
    HOMEM_TRANSGENERO(CategoriaAntropometrica.MASCULINO),
    MULHER_CISGENERO(CategoriaAntropometrica.FEMININO),
    MULHER_TRANSGENERO(CategoriaAntropometrica.FEMININO),
    GENERO_NAO_BINARIO(null),
    AGENERO(null),
    GENERO_FLUIDO(null),
    BIGENERO(null),
    MULHER_TRANSEXUAL(CategoriaAntropometrica.FEMININO),
    HOMEM_TRANSEXUAL(CategoriaAntropometrica.MASCULINO),
    POLIGENERO(null),
    GENERO_NEUTRO(null),
    PREFIRO_NAO_RESPONDER(null),
    INDEFINIDO(null);

    private final CategoriaAntropometrica categoriaAntropometrica;

    GeneroEnum(CategoriaAntropometrica categoriaAntropometrica) {
        this.categoriaAntropometrica = categoriaAntropometrica;
    }

    public CategoriaAntropometrica getCategoriaAntropometrica() {
        return categoriaAntropometrica;
    }

    public enum CategoriaAntropometrica {
        MASCULINO,
        FEMININO
    }
}
