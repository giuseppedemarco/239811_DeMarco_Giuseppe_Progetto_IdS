package com.progettoids_giuseppedemarco.infrastructure;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NoteFilmStorageTest {
    private final NoteFilmStorage storage = new NoteFilmStorage();

    @TempDir
    Path tempDir;

    @Test
    void saveELoadMantengonoDatiEssenziali() throws IOException {
        Path file = tempDir.resolve("films.txt");
        List<Film> films = List.of(
                film(1, "Tenet", "Christopher", "Nolan", 2020, "Sci-Fi", 5, StatoVisione.VISTO),
                film(null, "Natale al dimes", "Neri", "", 2000, "Commedia", 3, StatoVisione.DA_VEDERE)
        );

        storage.save(file, films);
        List<Film> loaded = storage.load(file);

        assertEquals(2, loaded.size());
        assertEquals("Tenet", loaded.get(0).getTitolo());
        assertEquals("Christopher Nolan", loaded.get(0).getRegista().getNomeCompleto());
        assertEquals(StatoVisione.VISTO, loaded.get(0).getStatoVisione());
        assertNull(loaded.get(1).getId());
        assertEquals("Natale al dimes", loaded.get(1).getTitolo());
        assertNull(loaded.get(1).getRegista().getCognome());
    }

    @Test
    void loadLanciaEccezioneSeRigaNonValida() throws IOException {
        Path file = tempDir.resolve("invalid.txt");
        Files.writeString(file, """
                id,titolo,regista_nome,regista_cognome,anno_uscita,genere,valutazione,stato_visione
                1,Tenet,Christopher,Nolan,2020,Sci-Fi,5
                """);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> storage.load(file));

        assertEquals("Riga file non valida alla linea 2", exception.getMessage());
    }

    private Film film(Integer id, String titolo, String nome, String cognome, int anno, String genere, int valutazione, StatoVisione stato) {
        return new Film(
                id,
                titolo,
                new Regista(null, nome, cognome.isBlank() ? null : cognome),
                anno,
                new Genere(null, genere),
                valutazione,
                stato
        );
    }
}
