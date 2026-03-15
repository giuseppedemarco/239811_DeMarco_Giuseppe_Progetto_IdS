package com.progettoids_giuseppedemarco.domain;

import java.util.Objects;

public class Regista {
    private final Integer id;
    private final String nome;
    private final String cognome;

    public Regista(Integer id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNomeCompleto() {
        return (nome + " " + (cognome == null ? "" : cognome)).trim();
    }

    public Regista withId(int newId) {
        return new Regista(newId, nome, cognome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Regista other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
