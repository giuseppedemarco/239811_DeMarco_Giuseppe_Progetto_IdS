package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortByStatoVisioneStrategy implements FilmSortStrategy {
    private static final Map<StatoVisione, Integer> ORDER = Map.of(
            StatoVisione.DA_VEDERE, 1,
            StatoVisione.IN_VISIONE, 2,
            StatoVisione.VISTO, 3
    );

    @Override
    public List<Film> doAlgorithm(List<Film> films) {
        return films.stream()
                .sorted(Comparator
                        .comparingInt((Film film) -> ORDER.getOrDefault(film.getStatoVisione(), Integer.MAX_VALUE))
                        .thenComparing(Film::getTitolo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
