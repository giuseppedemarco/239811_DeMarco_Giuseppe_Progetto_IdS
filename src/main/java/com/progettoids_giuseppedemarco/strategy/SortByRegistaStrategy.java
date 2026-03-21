package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByRegistaStrategy implements FilmSortStrategy {
    @Override
    public List<Film> doAlgorithm(List<Film> films) {
        return films.stream()
                .sorted(Comparator
                        .comparing((Film film) -> film.getRegista().getNomeCompleto(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Film::getTitolo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
}
