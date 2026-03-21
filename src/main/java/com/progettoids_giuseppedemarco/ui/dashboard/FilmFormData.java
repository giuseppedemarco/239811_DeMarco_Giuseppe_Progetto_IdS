package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.domain.StatoVisione;

public record FilmFormData(
        String titolo,
        String registaNome,
        String registaCognome,
        String annoDiUscita,
        String genere,
        String valutazione,
        StatoVisione statoVisione
) {
}
