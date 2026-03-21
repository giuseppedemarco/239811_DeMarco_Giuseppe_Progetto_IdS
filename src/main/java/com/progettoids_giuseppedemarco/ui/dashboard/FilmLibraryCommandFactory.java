package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.command.LibraryCommand;
import com.progettoids_giuseppedemarco.command.SimpleLibraryCommand;

import java.util.List;

public final class FilmLibraryCommandFactory {
    private FilmLibraryCommandFactory() {
    }

    public static List<LibraryCommand> create(FilmLibraryDashboardController controller) {
        return List.of(
                new SimpleLibraryCommand("Aggiungi film", controller::addFilm),
                new SimpleLibraryCommand("Aggiorna film", controller::updateFilm),
                new SimpleLibraryCommand("Rimuovi film", controller::removeFilm),
                new SimpleLibraryCommand("Ordina per titolo", controller::sortByTitle),
                new SimpleLibraryCommand("Ordina per regista", controller::sortByDirector),
                new SimpleLibraryCommand("Ordina per stato visione", controller::sortByStatus),
                new SimpleLibraryCommand("Ripristina elenco", controller::showRepositoryOrder),
                new SimpleLibraryCommand("Salva file", controller::saveLibrary)
        );
    }
}
