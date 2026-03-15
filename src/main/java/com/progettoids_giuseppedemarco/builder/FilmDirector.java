package com.progettoids_giuseppedemarco.builder;

import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

public class FilmDirector {

    public void buildFilmCompleto(
            FilmBuilder builder,
            String titolo,
            Regista regista,
            int annoDiUscita,
            Genere genere,
            int valutazionePersonale,
            StatoVisione statoVisione
    ) {
        builder.reset();
        builder.setTitolo(titolo);
        builder.setRegista(regista);
        builder.setAnnoDiUscita(annoDiUscita);
        builder.setGenere(genere);
        builder.setValutazionePersonale(valutazionePersonale);
        builder.setStatoVisione(statoVisione);
    }
}
