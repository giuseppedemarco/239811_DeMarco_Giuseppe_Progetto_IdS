package com.progettoids_giuseppedemarco.builder;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

public interface FilmBuilder {
    void reset();
    void setId(Integer id);
    void setTitolo(String titolo);
    void setRegista(Regista regista);
    void setAnnoDiUscita(int annoDiUscita);
    void setGenere(Genere genere);
    void setValutazionePersonale(int valutazionePersonale);
    void setStatoVisione(StatoVisione statoVisione);
    Film getResult();
}
