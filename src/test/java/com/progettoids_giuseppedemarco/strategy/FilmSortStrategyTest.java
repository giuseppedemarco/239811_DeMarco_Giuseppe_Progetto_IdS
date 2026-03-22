package com.progettoids_giuseppedemarco.strategy;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.Genere;
import com.progettoids_giuseppedemarco.domain.Regista;
import com.progettoids_giuseppedemarco.domain.StatoVisione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmSortStrategyTest {
    private List<Film> films;

    @BeforeEach
    void setUp() {
        films = List.of(
                film(1, "Tenet", "Christopher", "Nolan", 2000, 5, StatoVisione.VISTO),
                film(2, "Avengers", "Denis", "Villeneuve", 2016, 4, StatoVisione.DA_VEDERE),
                film(3, "Batman", "Christopher", "Nolan", 2017, 3, StatoVisione.IN_VISIONE),
                film(4, "Interstellar", "Denis", "Villeneuve", 2017, 5, StatoVisione.VISTO)
        );
    }

    @Test
    void sortByTitoloOrdinaSenzaDistinguereMaiuscoleEMinuscole() {
        List<Film> ordinati = new SortByTitoloStrategy().doAlgorithm(films);

        assertEquals(List.of("Avengers", "Batman", "Interstellar", "Tenet"),
                ordinati.stream().map(Film::getTitolo).toList());
    }

    @Test
    void sortByRegistaUsaNomeCompletoEPoiTitolo() {
        List<Film> ordinati = new SortByRegistaStrategy().doAlgorithm(films);

        assertEquals(List.of("Batman", "Tenet", "Avengers", "Interstellar"),
                ordinati.stream().map(Film::getTitolo).toList());
    }

    @Test
    void sortByValutazioneOrdinaInModoDecrescente() {
        List<Film> ordinati = new SortByValutazioneStrategy().doAlgorithm(films);

        assertEquals(List.of(5, 5, 4, 3),
                ordinati.stream().map(Film::getValutazionePersonale).toList());
    }

    @Test
    void sortByStatoVisioneRispettaOrdineDefinito() {
        List<Film> ordinati = new SortByStatoVisioneStrategy().doAlgorithm(films);

        assertEquals(List.of("Avengers", "Batman", "Interstellar", "Tenet"),
                ordinati.stream().map(Film::getTitolo).toList());
    }

    @Test
    void filmSortContextPermetteDiCambiareStrategia() {
        FilmSortContext context = new FilmSortContext(new SortByTitoloStrategy());

        List<Film> ordinatiPerTitolo = context.executeStrategy(films);
        context.setStrategy(new SortByAnnoStrategy());
        List<Film> ordinatiPerAnno = context.executeStrategy(films);

        assertEquals(List.of("Avengers", "Batman", "Interstellar", "Tenet"),
                ordinatiPerTitolo.stream().map(Film::getTitolo).toList());
        assertEquals(List.of("Tenet", "Avengers", "Batman", "Interstellar"),
                ordinatiPerAnno.stream().map(Film::getTitolo).toList());
    }

    private Film film(
            int id,
            String titolo,
            String nomeRegista,
            String cognomeRegista,
            int anno,
            int valutazione,
            StatoVisione statoVisione
    ) {
        return new Film(
                id,
                titolo,
                new Regista(id, nomeRegista, cognomeRegista),
                anno,
                new Genere(1, "Drama"),
                valutazione,
                statoVisione
        );
    }
}
