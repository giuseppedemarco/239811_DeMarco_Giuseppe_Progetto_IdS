package com.progettoids_giuseppedemarco.repository.db;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import com.progettoids_giuseppedemarco.infrastructure.DatabaseConnectionManager;
import com.progettoids_giuseppedemarco.repository.FilmRepository;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DatabaseFilmRepository implements FilmRepository {
    @Override
    public Film save(Film entity) {
        try (Connection connection = DatabaseConnectionManager.getInstance().openConnection()) {
            connection.setAutoCommit(false);
            if (entity.getId() == null || entity.getId() <= 0) {
                Film savedFilm = insertFilm(connection, entity);
                connection.commit();
                return savedFilm;
            }
            Film updatedFilm = updateFilm(connection, entity);
            connection.commit();
            return updatedFilm;
        } catch (SQLException e) {
            throw new IllegalStateException("salvataggio su database non riuscito", e);
        }
    }

    @Override
    public Optional<Film> findById(Integer id) {
        try (Connection connection = DatabaseConnectionManager.getInstance().openConnection()) {
            String query = baseSelectQuery() + " AND f.id = ? ORDER BY f.id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return Optional.empty();
                    }
                    return Optional.of(mapFilm(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("lettura film da database non riuscita", e);
        }
    }

    @Override
    public List<Film> findAll() {
        try (Connection connection = DatabaseConnectionManager.getInstance().openConnection()) {
            String query = baseSelectQuery() + " ORDER BY f.id";

            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                List<Film> films = new ArrayList<>();
                while (resultSet.next()) {
                    films.add(mapFilm(resultSet));
                }
                return films;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("lettura libreria da database non riuscita", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = DatabaseConnectionManager.getInstance().openConnection()) {
            connection.setAutoCommit(false);

            deleteByFilmId(connection, "stati", id);
            deleteByFilmId(connection, "valutazioni_personali", id);
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM film WHERE id = ?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException("rimozione film dal database non riuscita", e);
        }
    }

    private Film insertFilm(Connection connection, Film entity) throws SQLException {
        int registaId = findOrCreateRegista(connection, entity.getRegista());
        int genereId = findOrCreateGenere(connection, entity.getGenere().getNome());
        int filmId = nextId(connection, "film");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        String query = """
                INSERT INTO film (id, titolo, id_regista, anno_di_uscita, id_genere, created_at, updated_at, deleted_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, filmId);
            statement.setString(2, entity.getTitolo());
            statement.setInt(3, registaId);
            statement.setString(4, String.valueOf(entity.getAnnoDiUscita()));
            statement.setInt(5, genereId);
            statement.setTimestamp(6, now);
            statement.setTimestamp(7, now);
            statement.setTimestamp(8, null);
            statement.executeUpdate();
        }

        upsertStato(connection, filmId, entity.getStatoVisione(), now);
        upsertValutazione(connection, filmId, entity.getValutazionePersonale(), now);
        return entity.withId(filmId);
    }

    private Film updateFilm(Connection connection, Film entity) throws SQLException {
        int registaId = findOrCreateRegista(connection, entity.getRegista());
        int genereId = findOrCreateGenere(connection, entity.getGenere().getNome());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String query = """
                UPDATE film
                SET titolo = ?, id_regista = ?, anno_di_uscita = ?, id_genere = ?, updated_at = ?
                WHERE id = ? AND deleted_at IS NULL
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entity.getTitolo());
            statement.setInt(2, registaId);
            statement.setString(3, String.valueOf(entity.getAnnoDiUscita()));
            statement.setInt(4, genereId);
            statement.setTimestamp(5, now);
            statement.setInt(6, entity.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new IllegalStateException("film non trovato nel database: " + entity.getId());
            }
        }

        upsertStato(connection, entity.getId(), entity.getStatoVisione(), now);
        upsertValutazione(connection, entity.getId(), entity.getValutazionePersonale(), now);
        return entity;
    }

    private void upsertStato(Connection connection, int filmId, StatoVisione statoVisione, Timestamp now) throws SQLException {
        Integer statoId = findChildId(connection, "stati", filmId);
        if (statoId == null) {
            String insert = """
                    INSERT INTO stati (id, id_film, stato_visione, created_at, updated_at, deleted_at)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setInt(1, nextId(connection, "stati"));
                statement.setInt(2, filmId);
                statement.setString(3, statoVisione.name());
                statement.setTimestamp(4, now);
                statement.setTimestamp(5, now);
                statement.setTimestamp(6, null);
                statement.executeUpdate();
            }
            return;
        }

        String update = "UPDATE stati SET stato_visione = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, statoVisione.name());
            statement.setTimestamp(2, now);
            statement.setInt(3, statoId);
            statement.executeUpdate();
        }
    }

    private void upsertValutazione(Connection connection, int filmId, int valutazione, Timestamp now) throws SQLException {
        Integer valutazioneId = findChildId(connection, "valutazioni_personali", filmId);
        if (valutazioneId == null) {
            String insert = """
                    INSERT INTO valutazioni_personali (id, id_film, valutazione_personale, created_at, updated_at, deleted_at)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement statement = connection.prepareStatement(insert)) {
                statement.setInt(1, nextId(connection, "valutazioni_personali"));
                statement.setInt(2, filmId);
                statement.setString(3, String.valueOf(valutazione));
                statement.setString(4, now.toString());
                statement.setTimestamp(5, now);
                statement.setString(6, null);
                statement.executeUpdate();
            }
            return;
        }

        String update = "UPDATE valutazioni_personali SET valutazione_personale = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, String.valueOf(valutazione));
            statement.setTimestamp(2, now);
            statement.setInt(3, valutazioneId);
            statement.executeUpdate();
        }
    }

    private Integer findOrCreateRegista(Connection connection, Regista regista) throws SQLException {
        String select = """
                SELECT id
                FROM registi
                WHERE nome = ? AND ((cognome IS NULL AND ? IS NULL) OR cognome = ?)
                  AND deleted_at IS NULL
                LIMIT 1
                """;
        try (PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setString(1, regista.getNome());
            statement.setString(2, regista.getCognome());
            statement.setString(3, regista.getCognome());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }

        int registaId = nextId(connection, "registi");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String insert = """
                INSERT INTO registi (id, nome, cognome, created_at, updated_at, deleted_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setInt(1, registaId);
            statement.setString(2, regista.getNome());
            statement.setString(3, regista.getCognome());
            statement.setTimestamp(4, now);
            statement.setTimestamp(5, now);
            statement.setTimestamp(6, null);
            statement.executeUpdate();
        }
        return registaId;
    }

    private Integer findOrCreateGenere(Connection connection, String genere) throws SQLException {
        String select = "SELECT id FROM generi WHERE genere = ? AND deleted_at IS NULL LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setString(1, genere);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }

        int genereId = nextId(connection, "generi");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        String insert = """
                INSERT INTO generi (id, genere, created_at, updated_at, deleted_at)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setInt(1, genereId);
            statement.setString(2, genere);
            statement.setTimestamp(3, now);
            statement.setTimestamp(4, now);
            statement.setTimestamp(5, null);
            statement.executeUpdate();
        }
        return genereId;
    }

    private Integer findChildId(Connection connection, String tableName, int filmId) throws SQLException {
        String query = "SELECT id FROM %s WHERE id_film = ? AND deleted_at IS NULL LIMIT 1".formatted(tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, filmId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return null;
    }

    private void deleteByFilmId(Connection connection, String tableName, int filmId) throws SQLException {
        String query = "DELETE FROM %s WHERE id_film = ?".formatted(tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, filmId);
            statement.executeUpdate();
        }
    }

    private int nextId(Connection connection, String tableName) throws SQLException {
        String query = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM %s".formatted(tableName);
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("next_id");
        }
    }

    private String baseSelectQuery() {
        return """
                SELECT f.id,
                       f.titolo,
                       r.nome AS regista_nome,
                       r.cognome AS regista_cognome,
                       f.anno_di_uscita,
                       g.genere,
                       vp.valutazione_personale,
                       s.stato_visione
                FROM film f
                JOIN registi r ON r.id = f.id_regista
                JOIN generi g ON g.id = f.id_genere
                LEFT JOIN valutazioni_personali vp ON vp.id_film = f.id AND vp.deleted_at IS NULL
                LEFT JOIN stati s ON s.id_film = f.id AND s.deleted_at IS NULL
                WHERE f.deleted_at IS NULL
                """;
    }

    private Film mapFilm(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String titolo = resultSet.getString("titolo");
        String registaNome = resultSet.getString("regista_nome");
        String registaCognome = resultSet.getString("regista_cognome");
        int annoUscita = Integer.parseInt(resultSet.getString("anno_di_uscita"));
        String genere = resultSet.getString("genere");
        int valutazione = Integer.parseInt(resultSet.getString("valutazione_personale"));
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
