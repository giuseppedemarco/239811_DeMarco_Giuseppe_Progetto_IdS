package com.progettoids_giuseppedemarco.domain;

public class Film {
    private final Integer id;
    private final String titolo;
    private final Regista regista;
    private final int annoDiUscita;
    private final Genere genere;
    private final int valutazionePersonale;
    private final StatoVisione statoVisione;

    public Film(
            Integer id,
            String titolo,
            Regista regista,
            int annoDiUscita,
            Genere genere,
            int valutazionePersonale,
            StatoVisione statoVisione
    ) {
        this.id = id;
        this.titolo = titolo;
        this.regista = regista;
        this.annoDiUscita = annoDiUscita;
        this.genere = genere;
        this.valutazionePersonale = valutazionePersonale;
        this.statoVisione = statoVisione;
    }

    public Integer getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public Regista getRegista() {
        return regista;
    }

    public int getAnnoDiUscita() {
        return annoDiUscita;
    }

    public Genere getGenere() {
        return genere;
    }

    public int getValutazionePersonale() {
        return valutazionePersonale;
    }

    public StatoVisione getStatoVisione() {
        return statoVisione;
    }

    public Film withId(int newId) {
        return new Film(
                newId,
                titolo,
                regista,
                annoDiUscita,
                genere,
                valutazionePersonale,
                statoVisione
        );
    }
}
