package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.builder.ConcreteFilmBuilder;
import com.progettoids_giuseppedemarco.builder.FilmDirector;
import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import com.progettoids_giuseppedemarco.infrastructure.NoteFilmStorage;
import com.progettoids_giuseppedemarco.repository.inmemory.InMemoryFilmRepository;
import com.progettoids_giuseppedemarco.service.FilmLibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmLibraryDashboardControllerTest {
    private FilmLibraryDashboardController controller;
    private List<Film> shownFilms;
    private String feedback;
    private String error;
    private FilmLibraryService service;
    private FilmDirector director;
    private Regista regista;
    private Genere genere;

    @BeforeEach
    void setUp() {
        shownFilms = new ArrayList<>();
        feedback = null;
        error = null;
        service = new FilmLibraryService(new InMemoryFilmRepository());
        director = new FilmDirector();
        regista = new Regista(1, "Christopher", "Nolan");
        genere = new Genere(1, "Sci-Fi");

        service.aggiungiFilm(builderPer("Tenet", 2020, 5, StatoVisione.VISTO));
        service.aggiungiFilm(builderPer("Batman", 2022, 4, StatoVisione.IN_VISIONE));
        service.aggiungiFilm(builderPer("Avengers", 2012, 3, StatoVisione.DA_VEDERE));

        controller = new FilmLibraryDashboardController(
                null,
                service,
                new NoteFilmStorage(),
                Path.of("films.txt"),
                films -> shownFilms = new ArrayList<>(films),
                message -> feedback = message,
                message -> error = message
        );
    }

    @Test
    void showRepositoryOrderMostraElencoCorrente() {
        controller.showRepositoryOrder();

        assertEquals(3, shownFilms.size());
        assertEquals("Elenco aggiornato.", feedback);
        assertEquals("Tenet", shownFilms.get(0).getTitolo());
    }

    @Test
    void sortByTitleOrdinaFilmEImpostaFeedback() {
        controller.sortByTitle();

        assertEquals(List.of("Avengers", "Batman", "Tenet"),
                shownFilms.stream().map(Film::getTitolo).toList());
        assertEquals("Elenco ordinato per titolo.", feedback);
    }

    @Test
    void sortByDirectorOrdinaFilmEImpostaFeedback() {
        controller.sortByDirector();

        assertEquals(List.of("Avengers", "Batman", "Tenet"),
                shownFilms.stream().map(Film::getTitolo).toList());
        assertEquals("Elenco ordinato per regista.", feedback);
    }

    @Test
    void sortByStatusOrdinaFilmEImpostaFeedback() {
        controller.sortByStatus();

        assertEquals(List.of("Avengers", "Batman", "Tenet"),
                shownFilms.stream().map(Film::getTitolo).toList());
        assertEquals("Elenco ordinato per stato visione.", feedback);
        assertEquals(null, error);
    }

    private ConcreteFilmBuilder builderPer(String titolo, int anno, int valutazione, StatoVisione statoVisione) {
        ConcreteFilmBuilder builder = new ConcreteFilmBuilder();
        director.buildFilmCompleto(builder, titolo, regista, anno, genere, valutazione, statoVisione);
        return builder;
    }
}
