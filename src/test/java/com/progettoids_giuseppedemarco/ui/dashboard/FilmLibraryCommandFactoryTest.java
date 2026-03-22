package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.command.LibraryCommand;
import com.progettoids_giuseppedemarco.builder.ConcreteFilmBuilder;
import com.progettoids_giuseppedemarco.builder.FilmDirector;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import com.progettoids_giuseppedemarco.infrastructure.NoteFilmStorage;
import com.progettoids_giuseppedemarco.repository.inmemory.InMemoryFilmRepository;
import com.progettoids_giuseppedemarco.service.FilmLibraryService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmLibraryCommandFactoryTest {

    @Test
    void createRestituisceComandiAttesiNellOrdinePrevisto() {
        FilmLibraryService service = new FilmLibraryService(new InMemoryFilmRepository());
        FilmDirector director = new FilmDirector();
        ConcreteFilmBuilder builder = new ConcreteFilmBuilder();
        director.buildFilmCompleto(builder, "Tenet", new Regista(1, "Christopher", "Nolan"), 2020, new Genere(1, "Sci-Fi"), 5, StatoVisione.VISTO);
        service.aggiungiFilm(builder);

        List<String> shownTitles = new ArrayList<>();
        List<String> feedbacks = new ArrayList<>();
        FilmLibraryDashboardController controller = new FilmLibraryDashboardController(
                null,
                service,
                new NoteFilmStorage(),
                null,
                films -> shownTitles.clear(),
                feedbacks::add,
                feedbacks::add
        );

        List<LibraryCommand> commands = FilmLibraryCommandFactory.create(controller);

        assertEquals(List.of(
                "Aggiungi film",
                "Aggiorna film",
                "Rimuovi film",
                "Ordina per titolo",
                "Ordina per regista",
                "Ordina per stato visione",
                "Ripristina elenco",
                "Salva file"
        ), commands.stream().map(LibraryCommand::getLabel).toList());
    }
}
