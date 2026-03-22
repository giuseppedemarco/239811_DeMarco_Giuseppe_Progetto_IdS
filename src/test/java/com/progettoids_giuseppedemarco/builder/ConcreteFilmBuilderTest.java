package com.progettoids_giuseppedemarco.builder;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcreteFilmBuilderTest {
    private ConcreteFilmBuilder builder;
    private FilmDirector director;
    private Regista regista;
    private Genere genere;

    @BeforeEach
    void setUp() {
        builder = new ConcreteFilmBuilder();
        director = new FilmDirector();
        regista = new Regista(1, "Christopher", "Nolan");
        genere = new Genere(1, "Fantascienza");
    }

    @Test
    void getResultCostruisceFilmConCampiAttesi() {
        director.buildFilmCompleto(
                builder,
                "  Interstellar  ",
                regista,
                2010,
                genere,
                5,
                StatoVisione.VISTO
        );

        Film film = builder.getResult();

        assertEquals("Interstellar", film.getTitolo());
        assertEquals(regista, film.getRegista());
        assertEquals(2010, film.getAnnoDiUscita());
        assertEquals(genere, film.getGenere());
        assertEquals(5, film.getValutazionePersonale());
        assertEquals(StatoVisione.VISTO, film.getStatoVisione());
    }

    @Test
    void getResultLanciaEccezioneSeTitoloManca() {
        director.buildFilmCompleto(
                builder,
                "   ",
                regista,
                2010,
                genere,
                4,
                StatoVisione.DA_VEDERE
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, builder::getResult);

        assertEquals("titolo obbligatorio", exception.getMessage());
    }

    @Test
    void getResultLanciaEccezioneSeValutazioneFuoriRange() {
        director.buildFilmCompleto(
                builder,
                "Interstellar",
                regista,
                2014,
                genere,
                6,
                StatoVisione.VISTO
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, builder::getResult);

        assertEquals("valutazione tra 1 e 5", exception.getMessage());
    }
}
