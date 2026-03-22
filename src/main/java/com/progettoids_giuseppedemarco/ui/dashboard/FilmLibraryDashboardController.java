package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.builder.ConcreteFilmBuilder;
import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.infrastructure.NoteFilmStorage;
import com.progettoids_giuseppedemarco.service.FilmLibraryService;
import com.progettoids_giuseppedemarco.strategy.SortByRegistaStrategy;
import com.progettoids_giuseppedemarco.strategy.SortByStatoVisioneStrategy;
import com.progettoids_giuseppedemarco.strategy.SortByTitoloStrategy;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class FilmLibraryDashboardController {
    private final Component parentComponent;
    private final FilmLibraryService libraryService;
    private final NoteFilmStorage noteFilmStorage;
    private Path currentNotePath;
    private final Consumer<List<Film>> showFilms;
    private final Consumer<String> showFeedback;
    private final Consumer<String> showError;

    public FilmLibraryDashboardController(
            Component parentComponent,
            FilmLibraryService libraryService,
            NoteFilmStorage noteFilmStorage,
            Path currentNotePath,
            Consumer<List<Film>> showFilms,
            Consumer<String> showFeedback,
            Consumer<String> showError
    ) {
        this.parentComponent = parentComponent;
        this.libraryService = libraryService;
        this.noteFilmStorage = noteFilmStorage;
        this.currentNotePath = currentNotePath;
        this.showFilms = showFilms;
        this.showFeedback = showFeedback;
        this.showError = showError;
    }

    public void addFilm() {
        FilmFormData formData = FilmFormDialog.show(parentComponent, null);
        if (formData == null) {
            return;
        }

        try {
            ConcreteFilmBuilder builder = buildFilmBuilder(formData, null);
            Film savedFilm = libraryService.aggiungiFilm(builder);
            showRepositoryOrder();
            showFeedback.accept("Film aggiunto con successo: " + savedFilm.getTitolo());
        } catch (IllegalArgumentException e) {
            showError.accept(e.getMessage());
        }
    }

    public void updateFilm() {
        String idText = askFilmId("Aggiorna film", "Inserisci l'id del film da aggiornare:");
        if (idText == null || idText.isBlank()) {
            return;
        }

        try {
            int id = Integer.parseInt(idText.trim());
            Film existingFilm = libraryService.trovaFilmPerId(id);
            FilmFormData formData = FilmFormDialog.show(parentComponent, existingFilm);
            if (formData == null) {
                return;
            }

            ConcreteFilmBuilder builder = buildFilmBuilder(formData, id);
            Film updatedFilm = libraryService.aggiornaFilm(id, builder);
            showRepositoryOrder();
            showFeedback.accept("Film aggiornato: " + updatedFilm.getTitolo());
        } catch (NumberFormatException e) {
            showError.accept("L'id inserito non e' numerico.");
        } catch (NoSuchElementException | IllegalArgumentException e) {
            showError.accept(e.getMessage());
        }
    }

    public void removeFilm() {
        String idText = askFilmId("Rimuovi film", "Inserisci l'id del film da rimuovere:");
        if (idText == null || idText.isBlank()) {
            return;
        }

        try {
            int id = Integer.parseInt(idText.trim());
            Film film = libraryService.trovaFilmPerId(id);
            libraryService.rimuoviFilm(id);
            showRepositoryOrder();
            showFeedback.accept("Film rimosso: " + film.getTitolo());
        } catch (NumberFormatException e) {
            showError.accept("L'id inserito non e' numerico.");
        } catch (NoSuchElementException e) {
            showError.accept(e.getMessage());
        }
    }

    public void sortByTitle() {
        showSorted("Elenco ordinato per titolo.", libraryService.ordina(new SortByTitoloStrategy()));
    }

    public void sortByDirector() {
        showSorted("Elenco ordinato per regista.", libraryService.ordina(new SortByRegistaStrategy()));
    }

    public void sortByStatus() {
        showSorted("Elenco ordinato per stato visione.", libraryService.ordina(new SortByStatoVisioneStrategy()));
    }

    public void showRepositoryOrder() {
        showFilms.accept(new ArrayList<>(libraryService.listaFilm()));
        showFeedback.accept("Elenco aggiornato.");
    }

    public void saveLibrary() {
        try {
            if (currentNotePath == null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Salva libreria come file di testo");
                fileChooser.setFileFilter(new FileNameExtensionFilter("File di testo (*.txt)", "txt"));
                fileChooser.setSelectedFile(new File("libreria-film.txt"));

                int result = fileChooser.showSaveDialog(parentComponent);
                if (result != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".txt")) {
                    selectedFile = new File(path + ".txt");
                }
                currentNotePath = selectedFile.toPath();
            }

            noteFilmStorage.save(currentNotePath, libraryService.listaFilm());
            showFeedback.accept("Libreria salvata in: " + currentNotePath.toAbsolutePath());
        } catch (Exception e) {
            showError.accept("Salvataggio non riuscito: " + e.getMessage());
        }
    }

    private void showSorted(String message, List<Film> films) {
        showFilms.accept(new ArrayList<>(films));
        showFeedback.accept(message);
    }

    private ConcreteFilmBuilder buildFilmBuilder(FilmFormData formData, Integer id) {
        try {
            ConcreteFilmBuilder builder = new ConcreteFilmBuilder();
            if (id != null) {
                builder.setId(id);
            }
            builder.setTitolo(formData.titolo().trim());
            builder.setRegista(new Regista(null, formData.registaNome().trim(), normalizeOptional(formData.registaCognome())));
            builder.setAnnoDiUscita(Integer.parseInt(formData.annoDiUscita().trim()));
            builder.setGenere(new Genere(null, formData.genere().trim()));
            builder.setValutazionePersonale(Integer.parseInt(formData.valutazione().trim()));
            builder.setStatoVisione(formData.statoVisione());
            return builder;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Anno e valutazione devono essere numerici.");
        }
    }

    private String askFilmId(String title, String message) {
        return JOptionPane.showInputDialog(parentComponent, message, title, JOptionPane.QUESTION_MESSAGE);
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
