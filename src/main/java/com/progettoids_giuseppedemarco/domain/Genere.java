package com.progettoids_giuseppedemarco.domain;

import java.util.Objects;

public class Genere {
    private final Integer id;
    private final String nome;

    public Genere(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Genere withId(int newId) {
        return new Genere(newId, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Genere other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
