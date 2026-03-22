package com.progettoids_giuseppedemarco.service;

import com.progettoids_giuseppedemarco.builder.ConcreteFilmBuilder;
import com.progettoids_giuseppedemarco.builder.FilmDirector;
import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import com.progettoids_giuseppedemarco.repository.inmemory.InMemoryFilmRepository;
import com.progettoids_giuseppedemarco.strategy.SortByAnnoStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmLibraryServiceTest {
    private FilmLibraryService service;
    private FilmDirector director;
    private Regista regista;
    private Genere genere;

    @BeforeEach
    void setUp() {
        service = new FilmLibraryService(new InMemoryFilmRepository());
        director = new FilmDirector();
        regista = new Regista(1, "Hayao", "Miyazaki");
        genere = new Genere(1, "Animazione");
    }

    @Test
    void aggiungiFilmSalvaEAssegnaId() {
        Film salvato = service.aggiungiFilm(builderPer("Tenet", 2001, 5, StatoVisione.VISTO));

        assertEquals(1, salvato.getId());
        assertEquals("Tenet", service.trovaFilmPerId(1).getTitolo());
    }

    @Test
    void aggiornaFilmSostituisceDatiMantenendoId() {
        Film iniziale = service.aggiungiFilm(builderPer("Batman", 1984, 4, StatoVisione.VISTO));

        Film aggiornato = service.aggiornaFilm(
                iniziale.getId(),
                builderPer("Avengers", 2004, 5, StatoVisione.IN_VISIONE)
        );

        assertEquals(iniziale.getId(), aggiornato.getId());
        assertEquals("Avengers", aggiornato.getTitolo());
        assertEquals(2004, aggiornato.getAnnoDiUscita());
        assertEquals(StatoVisione.IN_VISIONE, aggiornato.getStatoVisione());
    }

    @Test
    void aggiornaFilmLanciaEccezioneSeIdAssente() {
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.aggiornaFilm(999, builderPer("Interstellar", 1992, 5, StatoVisione.VISTO))
        );

        assertEquals("film non trovato: 999", exception.getMessage());
    }

    @Test
    void ordinaRestituisceFilmOrdinatiPerAnno() {
        service.aggiungiFilm(builderPer("Tenet", 2008, 4, StatoVisione.VISTO));
        service.aggiungiFilm(builderPer("Batman", 1988, 5, StatoVisione.VISTO));
        service.aggiungiFilm(builderPer("Avengers", 1989, 4, StatoVisione.VISTO));

        List<Film> ordinati = service.ordina(new SortByAnnoStrategy());

        assertEquals(List.of("Batman", "Avengers", "Tenet"),
                ordinati.stream().map(Film::getTitolo).toList());
    }

    @Test
    void rimuoviFilmEliminaIlFilmDallaLista() {
        Film salvato = service.aggiungiFilm(builderPer("Natale al dimes", 1997, 5, StatoVisione.VISTO));

        service.rimuoviFilm(salvato.getId());

        assertTrue(service.listaFilm().isEmpty());
    }

    @Test
    void trovaFilmPerIdLanciaEccezioneSeIdAssente() {
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.trovaFilmPerId(321)
        );

        assertEquals("film non trovato: 321", exception.getMessage());
    }

    private ConcreteFilmBuilder builderPer(String titolo, int anno, int valutazione, StatoVisione statoVisione) {
        ConcreteFilmBuilder builder = new ConcreteFilmBuilder();
        director.buildFilmCompleto(builder, titolo, regista, anno, genere, valutazione, statoVisione);
        return builder;
    }
}
