package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;

import java.util.List;

public interface FilmSortStrategy {
    List<Film> doAlgorithm(List<Film> films);
}
