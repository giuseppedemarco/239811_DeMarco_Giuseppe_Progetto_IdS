package com.progettoids_giuseppedemarco.command;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleLibraryCommandTest {

    @Test
    void getLabelRestituisceEtichettaPassataNelCostruttore() {
        SimpleLibraryCommand command = new SimpleLibraryCommand("Salva libreria", () -> { });

        assertEquals("Salva libreria", command.getLabel());
    }

    @Test
    void executeInvocaLAzioneAssociata() {
        AtomicInteger counter = new AtomicInteger();
        SimpleLibraryCommand command = new SimpleLibraryCommand("Incrementa", counter::incrementAndGet);

        command.execute();

        assertEquals(1, counter.get());
    }
}
