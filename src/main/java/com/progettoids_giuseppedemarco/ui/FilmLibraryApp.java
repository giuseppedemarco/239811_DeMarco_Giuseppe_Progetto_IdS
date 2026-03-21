package com.progettoids_giuseppedemarco.ui;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.infrastructure.DatabaseConnectionManager;
import com.progettoids_giuseppedemarco.infrastructure.NoteFilmStorage;
import com.progettoids_giuseppedemarco.repository.FilmRepository;
import com.progettoids_giuseppedemarco.repository.inmemory.InMemoryFilmRepository;
import com.progettoids_giuseppedemarco.service.FilmLibraryService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FilmLibraryApp extends Application {
    private final NoteFilmStorage noteFilmStorage = new NoteFilmStorage();

    private FilmLibraryService libraryService = new FilmLibraryService(new InMemoryFilmRepository());
    private Path currentNotePath;

    public static void launchApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Benvenuto nella libreria di film");

        Label subtitleLabel = new Label("Scegli la sorgente iniziale dei dati per continuare.");
        subtitleLabel.setWrapText(true);
        subtitleLabel.setMaxWidth(280);

        Button localButton = new Button("Carica file");
        localButton.setOnAction(event -> loadLocalLibrary(stage));
        localButton.setMaxWidth(Double.MAX_VALUE);

        Button dbButton = new Button("Carica da DB");
        dbButton.setOnAction(event -> loadFromDatabase());
        dbButton.setMaxWidth(Double.MAX_VALUE);

        VBox root = new VBox(12, titleLabel, subtitleLabel, localButton, dbButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));
        root.setPrefWidth(320);

        Scene scene = new Scene(root, 360, 220);
        stage.setTitle("Libreria di Film");
        stage.setScene(scene);
        stage.setMinWidth(340);
        stage.setMinHeight(220);
        stage.show();
    }

    private void loadLocalLibrary(Stage stage) {
        FileChooser fileChooser = createTextFileChooser("Seleziona il file della libreria");
        applyCurrentFileLocation(fileChooser);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return;
        }

        try {
            List<Film> films = noteFilmStorage.load(selectedFile.toPath());
            FilmRepository filmRepository = new InMemoryFilmRepository();
            for (Film film : films) {
                filmRepository.save(film);
            }

            libraryService = new FilmLibraryService(filmRepository);
            currentNotePath = selectedFile.toPath();
            FilmLibraryDashboard.open(libraryService, noteFilmStorage, currentNotePath);
            stage.close();
        } catch (IOException | IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Caricamento file non riuscito", e.getMessage());
        }
    }

    private void loadFromDatabase() {
        try (Connection connection = DatabaseConnectionManager.getInstance().openConnection()) {
            showAlert(Alert.AlertType.INFORMATION, "Connessione al database riuscita", "Database raggiunto correttamente usando i parametri di db.properties.");
        } catch (IllegalStateException | SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Connessione al database non riuscita", e.getMessage());
        }
    }

    private FileChooser createTextFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File di testo", "*.txt"));
        return fileChooser;
    }

    private void applyCurrentFileLocation(FileChooser fileChooser) {
        if (currentNotePath == null) {
            return;
        }

        Path parent = currentNotePath.toAbsolutePath().getParent();
        if (parent != null && parent.toFile().exists()) {
            fileChooser.setInitialDirectory(parent.toFile());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
