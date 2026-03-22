package com.progettoids_giuseppedemarco.infrastructure;

import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseFilmStorageTest {

    @Test
    void parseStatoVisioneAccettaValoreConSpazi() throws Exception {
        DatabaseFilmStorage storage = new DatabaseFilmStorage();
        Method method = DatabaseFilmStorage.class.getDeclaredMethod("parseStatoVisione", String.class);
        method.setAccessible(true);

        StatoVisione stato = (StatoVisione) method.invoke(storage, "in visione");

        assertEquals(StatoVisione.IN_VISIONE, stato);
    }

    @Test
    void normalizeOptionalRitornaNullSeStringaVuota() throws Exception {
        DatabaseFilmStorage storage = new DatabaseFilmStorage();
        Method method = DatabaseFilmStorage.class.getDeclaredMethod("normalizeOptional", String.class);
        method.setAccessible(true);

        Object value = method.invoke(storage, "   ");

        assertNull(value);
    }

    @Test
    void parseStatoVisioneLanciaEccezioneSeValoreManca() throws Exception {
        DatabaseFilmStorage storage = new DatabaseFilmStorage();
        Method method = DatabaseFilmStorage.class.getDeclaredMethod("parseStatoVisione", String.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> method.invoke(storage, " "));

        assertEquals("stato_visione mancante nel database.", exception.getCause().getMessage());
    }
}
