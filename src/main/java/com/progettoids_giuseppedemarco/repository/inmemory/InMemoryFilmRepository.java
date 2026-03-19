package com.progettoids_giuseppedemarco.repository.inmemory;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.repository.FilmRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryFilmRepository implements FilmRepository {
    private final Map<Integer, Film> filmStore = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Film save(Film entity) {
        Integer id = entity.getId();
        Film persisted = entity;

        if (id == null || id <= 0) {
            persisted = entity.withId(nextId.getAndIncrement());
            id = persisted.getId();
        } else {
            final int candidate = id + 1;
            nextId.updateAndGet(current -> Math.max(current, candidate));
        }

        filmStore.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return Optional.ofNullable(filmStore.get(id));
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(filmStore.values());
    }

    @Override
    public void deleteById(Integer id) {
        filmStore.remove(id);
    }
}
