package com.progettoids_giuseppedemarco.dto;

import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoRecordTest {

    @Test
    void createDtosEspongonoValoriAttesi() {
        CreateFilmDto filmDto = new CreateFilmDto("Tenet", 1, 2020, 2, 5, StatoVisione.VISTO);
        CreateGenereDto genereDto = new CreateGenereDto("Azione");
        CreateRegistaDto registaDto = new CreateRegistaDto("Christopher", "Nolan");

        assertEquals("Tenet", filmDto.titolo());
        assertEquals(1, filmDto.registaId());
        assertEquals(2020, filmDto.annoDiUscita());
        assertEquals(2, filmDto.genereId());
        assertEquals(5, filmDto.valutazionePersonale());
        assertEquals(StatoVisione.VISTO, filmDto.statoVisione());
        assertEquals("Azione", genereDto.nome());
        assertEquals("Christopher", registaDto.nome());
        assertEquals("Nolan", registaDto.cognome());
    }

    @Test
    void updateDtosEspongonoValoriAttesi() {
        UpdateFilmDto filmDto = new UpdateFilmDto("Batman", 3, 2022, 4, 4, StatoVisione.IN_VISIONE);
        UpdateGenereDto genereDto = new UpdateGenereDto("Fantasy");
        UpdateRegistaDto registaDto = new UpdateRegistaDto("Matt", "Reeves");

        assertEquals("Batman", filmDto.titolo());
        assertEquals(3, filmDto.registaId());
        assertEquals(2022, filmDto.annoDiUscita());
        assertEquals(4, filmDto.genereId());
        assertEquals(4, filmDto.valutazionePersonale());
        assertEquals(StatoVisione.IN_VISIONE, filmDto.statoVisione());
        assertEquals("Fantasy", genereDto.nome());
        assertEquals("Matt", registaDto.nome());
        assertEquals("Reeves", registaDto.cognome());
    }

    @Test
    void responseDtosEspongonoValoriAttesi() {
        FilmResponseDto filmDto = new FilmResponseDto(7, "Interstellar", 1, "Christopher Nolan", 2014, 2, "Sci-Fi", 5, StatoVisione.VISTO);
        GenereResponseDto genereDto = new GenereResponseDto(2, "Sci-Fi");
        RegistaResponseDto registaDto = new RegistaResponseDto(1, "Christopher", "Nolan", "Christopher Nolan");

        assertEquals(7, filmDto.id());
        assertEquals("Interstellar", filmDto.titolo());
        assertEquals(1, filmDto.registaId());
        assertEquals("Christopher Nolan", filmDto.registaNomeCompleto());
        assertEquals(2014, filmDto.annoDiUscita());
        assertEquals(2, filmDto.genereId());
        assertEquals("Sci-Fi", filmDto.genereNome());
        assertEquals(5, filmDto.valutazionePersonale());
        assertEquals(StatoVisione.VISTO, filmDto.statoVisione());
        assertEquals(2, genereDto.id());
        assertEquals("Sci-Fi", genereDto.nome());
        assertEquals(1, registaDto.id());
        assertEquals("Christopher", registaDto.nome());
        assertEquals("Nolan", registaDto.cognome());
        assertEquals("Christopher Nolan", registaDto.nomeCompleto());
    }
}
