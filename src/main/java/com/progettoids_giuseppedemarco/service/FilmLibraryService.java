package com.progettoids_giuseppedemarco.service;

import com.progettoids_giuseppedemarco.builder.FilmBuilder;
import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.repository.FilmRepository;
import com.progettoids_giuseppedemarco.strategy.FilmSortContext;
import com.progettoids_giuseppedemarco.strategy.FilmSortStrategy;

import java.util.List;
import java.util.NoSuchElementException;

public class FilmLibraryService {
    private final FilmRepository filmRepository;

    public FilmLibraryService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film aggiungiFilm(FilmBuilder builder) {
        Film film = builder.getResult();
        return filmRepository.save(film);
    }

    public Film aggiornaFilm(int id, FilmBuilder builder) {
        filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("film non trovato: " + id));
        builder.setId(id);
        Film filmAggiornato = builder.getResult();
        return filmRepository.save(filmAggiornato);
    }

    public void rimuoviFilm(int id) {
        filmRepository.deleteById(id);
    }

    public List<Film> listaFilm() {
        return filmRepository.findAll();
    }

    public Film trovaFilmPerId(int id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("film non trovato: " + id));
    }

    public List<Film> ordina(FilmSortStrategy strategy) {
        FilmSortContext context = new FilmSortContext(strategy);
        return context.executeStrategy(filmRepository.findAll());
    }
}
