package com.progettoids_giuseppedemarco.infrastructure;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NoteFilmStorage {
    private static final String HEADER = "id,titolo,regista_nome,regista_cognome,anno_uscita,genere,valutazione,stato_visione";

    public List<Film> load(Path notePath) throws IOException {
        List<String> lines = Files.readAllLines(notePath, StandardCharsets.UTF_8);
        List<Film> films = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line == null || line.isBlank() || isHeader(line)) {
                continue;
            }

            List<String> tokens = splitLine(line);
            if (tokens.size() != 8) {
                throw new IllegalArgumentException("Riga file non valida alla linea " + (i + 1));
            }

            films.add(new Film(
                    parseId(tokens.get(0)),
                    tokens.get(1),
                    new Regista(null, tokens.get(2), emptyToNull(tokens.get(3))),
                    Integer.parseInt(tokens.get(4)),
                    new Genere(null, tokens.get(5)),
                    Integer.parseInt(tokens.get(6)),
                    StatoVisione.valueOf(tokens.get(7).toUpperCase())
            ));
        }

        return films;
    }

    public void save(Path notePath, List<Film> films) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Film film : films) {
            lines.add(String.join(",",
                    text(film.getId()),
                    text(film.getTitolo()),
                    text(film.getRegista().getNome()),
                    text(film.getRegista().getCognome()),
                    text(film.getAnnoDiUscita()),
                    text(film.getGenere().getNome()),
                    text(film.getValutazionePersonale()),
                    film.getStatoVisione().name()
            ));
        }
        Files.write(notePath, lines, StandardCharsets.UTF_8);
    }

    private boolean isHeader(String line) {
        return line != null && line.trim().equalsIgnoreCase(HEADER);
    }

    private Integer parseId(String value) {
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : Integer.parseInt(trimmed);
    }

    private String emptyToNull(String value) {
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String text(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(",", " ").trim();
    }

    private List<String> splitLine(String line) {
        List<String> fields = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(line + ",", ",", true);
        boolean div = false;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (",".equals(token)) {
                if (div || fields.isEmpty()) {
                    fields.add("");
                }
                div = true;
            } else {
                fields.add(token.trim());
                div = false;
            }
        }

        return fields;
    }
}
