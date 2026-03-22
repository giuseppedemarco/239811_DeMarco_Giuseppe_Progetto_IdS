package com.progettoids_giuseppedemarco.repository.inmemory;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryFilmRepositoryTest {
    private InMemoryFilmRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryFilmRepository();
    }

    @Test
    void saveAssegnaIdProgressivoQuandoManca() {
        Film primoFilm = film(null, "Avengers", 2016);
        Film secondoFilm = film(null, "Batman", 2021);

        Film primoSalvato = repository.save(primoFilm);
        Film secondoSalvato = repository.save(secondoFilm);

        assertEquals(1, primoSalvato.getId());
        assertEquals(2, secondoSalvato.getId());
    }

    @Test
    void saveAggiornaContatoreDopoIdEsplicito() {
        repository.save(film(10, "Interstellar", 1982));

        Film salvato = repository.save(film(null, "Natale al dimes", 2017));

        assertEquals(11, salvato.getId());
    }

    @Test
    void deleteByIdRimuoveIlFilmSalvato() {
        Film salvato = repository.save(film(null, "Tenet", 2020));

        repository.deleteById(salvato.getId());

        assertFalse(repository.findById(salvato.getId()).isPresent());
        assertTrue(repository.findAll().isEmpty());
    }

    private Film film(Integer id, String titolo, int anno) {
        return new Film(
                id,
                titolo,
                new Regista(1, "Denis", "Villeneuve"),
                anno,
                new Genere(1, "Sci-Fi"),
                4,
                StatoVisione.VISTO
        );
    }
}
