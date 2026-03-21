package com.progettoids_giuseppedemarco.infrastructure;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DatabaseFilmStorage {
    private static final List<String> REQUIRED_COLUMNS = List.of(
            "id",
            "titolo",
            "regista_nome",
            "regista_cognome",
            "anno_uscita",
            "genere",
            "valutazione",
            "stato_visione"
    );

    public List<Film> load(Connection connection) throws SQLException {
        String tableName = findFilmTable(connection);
        String query = """
                SELECT id, titolo, regista_nome, regista_cognome, anno_uscita, genere, valutazione, stato_visione
                FROM %s
                ORDER BY id
                """.formatted(tableName);

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Film> films = new ArrayList<>();
            while (resultSet.next()) {
                films.add(mapFilm(resultSet));
            }
            return films;
        }
    }

    private String findFilmTable(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet tables = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"})) {
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (tableName != null && hasRequiredColumns(metaData, connection, tableName)) {
                    return tableName;
                }
            }
        }

        throw new IllegalStateException("tabella film non trovata nel database.");
    }

    private boolean hasRequiredColumns(DatabaseMetaData metaData, Connection connection, String tableName) throws SQLException {
        Set<String> availableColumns = new HashSet<>();
        try (ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, "%")) {
            while (columns.next()) {
                availableColumns.add(columns.getString("COLUMN_NAME").toLowerCase(Locale.ROOT));
            }
        }

        return availableColumns.containsAll(REQUIRED_COLUMNS);
    }

    private Film mapFilm(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String titolo = resultSet.getString("titolo");
        String registaNome = resultSet.getString("regista_nome");
        String registaCognome = resultSet.getString("regista_cognome");
        int annoUscita = resultSet.getInt("anno_uscita");
        String genere = resultSet.getString("genere");
        int valutazione = resultSet.getInt("valutazione");
        StatoVisione statoVisione = parseStatoVisione(resultSet.getString("stato_visione"));

        return new Film(
                id,
                titolo,
                new Regista(null, registaNome, normalizeOptional(registaCognome)),
                annoUscita,
                new Genere(null, genere),
                valutazione,
                statoVisione
        );
    }

    private StatoVisione parseStatoVisione(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException("stato_visione mancante nel database.");
        }

        String normalized = rawValue.trim()
                .toUpperCase(Locale.ROOT)
                .replace(' ', '_');
        return StatoVisione.valueOf(normalized);
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
