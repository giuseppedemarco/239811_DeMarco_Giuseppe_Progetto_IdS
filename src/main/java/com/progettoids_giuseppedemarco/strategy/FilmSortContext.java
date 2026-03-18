package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;

import java.util.List;

public class FilmSortContext {
    private FilmSortStrategy strategy;

    public FilmSortContext(FilmSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FilmSortStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Film> executeStrategy(List<Film> films) {
        return strategy.doAlgorithm(films);
    }
}
