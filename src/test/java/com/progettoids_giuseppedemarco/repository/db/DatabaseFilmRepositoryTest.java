package com.progettoids_giuseppedemarco.repository.db;

import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseFilmRepositoryTest {

    @Test
    void baseSelectQueryContieneJoinPrincipali() throws Exception {
        DatabaseFilmRepository repository = new DatabaseFilmRepository();
        Method method = DatabaseFilmRepository.class.getDeclaredMethod("baseSelectQuery");
        method.setAccessible(true);

        String query = (String) method.invoke(repository);

        assertTrue(query.contains("FROM film f"));
        assertTrue(query.contains("JOIN registi r"));
        assertTrue(query.contains("JOIN generi g"));
        assertTrue(query.contains("LEFT JOIN stati s"));
    }

    @Test
    void parseStatoVisioneNormalizzaValori() throws Exception {
        DatabaseFilmRepository repository = new DatabaseFilmRepository();
        Method method = DatabaseFilmRepository.class.getDeclaredMethod("parseStatoVisione", String.class);
        method.setAccessible(true);

        StatoVisione stato = (StatoVisione) method.invoke(repository, "da vedere");

        assertEquals(StatoVisione.DA_VEDERE, stato);
    }

    @Test
    void normalizeOptionalRimuoveSpazi() throws Exception {
        DatabaseFilmRepository repository = new DatabaseFilmRepository();
        Method method = DatabaseFilmRepository.class.getDeclaredMethod("normalizeOptional", String.class);
        method.setAccessible(true);

        String value = (String) method.invoke(repository, "  Nolan ");
        Object empty = method.invoke(repository, "   ");

        assertEquals("Nolan", value);
        assertNull(empty);
    }

    @Test
    void parseStatoVisioneLanciaEccezioneSeVuoto() throws Exception {
        DatabaseFilmRepository repository = new DatabaseFilmRepository();
        Method method = DatabaseFilmRepository.class.getDeclaredMethod("parseStatoVisione", String.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> method.invoke(repository, ""));

        assertEquals("stato_visione mancante nel database.", exception.getCause().getMessage());
    }
}
