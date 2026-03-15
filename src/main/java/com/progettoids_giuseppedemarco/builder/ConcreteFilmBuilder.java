package com.progettoids_giuseppedemarco.builder;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

public class ConcreteFilmBuilder implements FilmBuilder {
    private Integer id;
    private String titolo;
    private Regista regista;
    private int annoDiUscita;
    private Genere genere;
    private int valutazionePersonale;
    private StatoVisione statoVisione;

    public ConcreteFilmBuilder() {
        reset();
    }

    public static ConcreteFilmBuilder from(Film film) {
        ConcreteFilmBuilder builder = new ConcreteFilmBuilder();
        builder.setId(film.getId());
        builder.setTitolo(film.getTitolo());
        builder.setRegista(film.getRegista());
        builder.setAnnoDiUscita(film.getAnnoDiUscita());
        builder.setGenere(film.getGenere());
        builder.setValutazionePersonale(film.getValutazionePersonale());
        builder.setStatoVisione(film.getStatoVisione());
        return builder;
    }

    @Override
    public void reset() {
        id = null;
        titolo = null;
        regista = null;
        annoDiUscita = 0;
        genere = null;
        valutazionePersonale = 0;
        statoVisione = StatoVisione.DA_VEDERE;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public void setRegista(Regista regista) {
        this.regista = regista;
    }

    @Override
    public void setAnnoDiUscita(int annoDiUscita) {
        this.annoDiUscita = annoDiUscita;
    }

    @Override
    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    @Override
    public void setValutazionePersonale(int valutazionePersonale) {
        this.valutazionePersonale = valutazionePersonale;
    }

    @Override
    public void setStatoVisione(StatoVisione statoVisione) {
        this.statoVisione = statoVisione;
    }

    @Override
    public Film getResult() {
        if (titolo == null || titolo.isBlank()) {
            throw new IllegalArgumentException("titolo obbligatorio");
        }
        if (regista == null) {
            throw new IllegalArgumentException("regista obbligatorio");
        }
        if (genere == null) {
            throw new IllegalArgumentException("genere obbligatorio");
        }
        if (valutazionePersonale < 1 || valutazionePersonale > 5) {
            throw new IllegalArgumentException("valutazione tra 1 e 5");
        }

        Film film = new Film(
                id,
                titolo.trim(),
                regista,
                annoDiUscita,
                genere,
                valutazionePersonale,
                statoVisione
        );
        reset();
        return film;
    }
}

