package com.progettoids_giuseppedemarco.dto;

import com.progettoids_giuseppedemarco.domain.StatoVisione;

public record UpdateFilmDto(
        String titolo,
        Integer registaId,
        int annoDiUscita,
        Integer genereId,
        int valutazionePersonale,
        StatoVisione statoVisione
) {
}
