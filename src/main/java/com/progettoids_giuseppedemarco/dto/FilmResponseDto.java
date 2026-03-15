package com.progettoids_giuseppedemarco.dto;

import com.progettoids_giuseppedemarco.domain.StatoVisione;

public record FilmResponseDto(
        Integer id,
        String titolo,
        Integer registaId,
        String registaNomeCompleto,
        int annoDiUscita,
        Integer genereId,
        String genereNome,
        int valutazionePersonale,
        StatoVisione statoVisione
) {
}
