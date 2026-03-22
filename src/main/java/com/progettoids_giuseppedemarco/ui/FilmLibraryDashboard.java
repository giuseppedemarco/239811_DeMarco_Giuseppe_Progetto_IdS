package com.progettoids_giuseppedemarco.ui;

import com.progettoids_giuseppedemarco.command.LibraryCommand;
import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.infrastructure.NoteFilmStorage;
import com.progettoids_giuseppedemarco.service.FilmLibraryService;
import com.progettoids_giuseppedemarco.ui.dashboard.FilmLibraryCommandFactory;
import com.progettoids_giuseppedemarco.ui.dashboard.FilmLibraryDashboardController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FilmLibraryDashboard extends JFrame {
    private final JTextArea filmsArea = new JTextArea();
    private final JLabel feedbackLabel = new JLabel("Operazioni disponibili sulla libreria caricata.");
    private final FilmLibraryDashboardController controller;

    private List<Film> displayedFilms;

    public FilmLibraryDashboard(FilmLibraryService libraryService, NoteFilmStorage noteFilmStorage, Path currentNotePath) {
        this.displayedFilms = new ArrayList<>(libraryService.listaFilm());
        this.controller = new FilmLibraryDashboardController(
                this,
                libraryService,
                noteFilmStorage,
                currentNotePath,
                this::setDisplayedFilms,
                this::setFeedback,
                this::showError
        );

        setTitle("Gestione Libreria Film");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(780, 520));
        setLayout(new BorderLayout(8, 8));

        add(buildToolbar(), BorderLayout.NORTH);
        add(buildCenterContent(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        refreshFilmsPanel();
    }

    public static void open(FilmLibraryService libraryService, NoteFilmStorage noteFilmStorage, Path currentNotePath) {
        SwingUtilities.invokeLater(() -> {
            FilmLibraryDashboard dashboard = new FilmLibraryDashboard(libraryService, noteFilmStorage, currentNotePath);
            dashboard.setVisible(true);
        });
    }

    private JPanel buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));

        for (LibraryCommand command : FilmLibraryCommandFactory.create(controller)) {
            JButton button = new JButton(command.getLabel());
            button.addActionListener(event -> command.execute());
            toolbar.add(button);
        }

        return toolbar;
    }

    private JScrollPane buildCenterContent() {
        filmsArea.setEditable(false);
        filmsArea.setLineWrap(true);
        filmsArea.setWrapStyleWord(true);
        filmsArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(filmsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Elenco film"));
        return scrollPane;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        footer.add(feedbackLabel, BorderLayout.WEST);
        return footer;
    }

    private void setDisplayedFilms(List<Film> films) {
        displayedFilms = new ArrayList<>(films);
        refreshFilmsPanel();
    }

    private void setFeedback(String message) {
        feedbackLabel.setText(message);
    }

    private void refreshFilmsPanel() {
        if (displayedFilms.isEmpty()) {
            filmsArea.setText("Nessun film disponibile.");
            filmsArea.setCaretPosition(0);
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (Film film : displayedFilms) {
            builder.append("ID: ").append(film.getId()).append('\n');
            builder.append("Titolo: ").append(film.getTitolo()).append('\n');
            builder.append("Regista: ").append(film.getRegista().getNomeCompleto()).append('\n');
            builder.append("Anno: ").append(film.getAnnoDiUscita()).append('\n');
            builder.append("Genere: ").append(film.getGenere().getNome()).append('\n');
            builder.append("Valutazione: ").append(film.getValutazionePersonale()).append("/5").append('\n');
            builder.append("Stato: ").append(film.getStatoVisione()).append("\n\n");
        }
        filmsArea.setText(builder.toString().trim());
        filmsArea.setCaretPosition(0);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Operazione non riuscita", JOptionPane.ERROR_MESSAGE);
    }
}
