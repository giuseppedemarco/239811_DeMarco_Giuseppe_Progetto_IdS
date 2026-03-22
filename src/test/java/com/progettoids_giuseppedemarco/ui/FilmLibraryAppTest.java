package com.progettoids_giuseppedemarco.ui;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmLibraryAppTest {

    @Test
    void buildDetailedMessageIncludeMessaggioECausa() throws Exception {
        FilmLibraryApp app = new FilmLibraryApp();
        Method method = FilmLibraryApp.class.getDeclaredMethod("buildDetailedMessage", Throwable.class);
        method.setAccessible(true);

        String message = (String) method.invoke(app,
                new IllegalStateException("Errore principale", new RuntimeException("Dettaglio interno")));

        assertTrue(message.contains("Errore principale"));
        assertTrue(message.contains("Dettaglio interno"));
    }

    @Test
    void buildDetailedMessageUsaFallbackSeMessaggiAssenti() throws Exception {
        FilmLibraryApp app = new FilmLibraryApp();
        Method method = FilmLibraryApp.class.getDeclaredMethod("buildDetailedMessage", Throwable.class);
        method.setAccessible(true);

        String message = (String) method.invoke(app, new IllegalStateException((String) null));

        assertEquals("Errore sconosciuto durante il caricamento da database.", message);
    }
}
