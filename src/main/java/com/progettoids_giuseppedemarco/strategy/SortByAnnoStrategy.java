package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByAnnoStrategy implements FilmSortStrategy {
    @Override
    public List<Film> doAlgorithm(List<Film> films) {
        return films.stream()
                .sorted(Comparator.comparingInt(Film::getAnnoDiUscita))
                .collect(Collectors.toList());
    }
}
