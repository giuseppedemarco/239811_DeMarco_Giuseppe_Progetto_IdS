package src.main.java.utils.builders;

import java.util.Arrays;

import src.main.java.commands.cmd.CreateCommand;

public class CreateCommandBuilder {
    
    /**
     * Crea un'istanza di {@link CreateCommand} basata sui parametri forniti.
     *
     * @param p array di stringhe contenente i valori del libro nell'ordine:
        nome, autore, isbn, genere, valutazione, statoLettura
     * @return un'istanza di {@link CreateCommand} pronta per l'esecuzione 
     */
    public CreateCommand create(String[] p) {
        String q = verifica(p);
        return new CreateCommand(q, p);
    }

    /**
     * Verifica i parametri forniti per la creazione del libro.
     *
     * @param parametri array di stringhe contenente i valori del libro
     * @return la query SQL di inserimento pronta
     * @throws IllegalArgumentException se uno dei controlli fallisce
     */
    private String verifica(String[] parametri) {
        String query = "INSERT INTO libreria (nome, autore, isbn, genere, valutazione, statoLettura) VALUES (?, ?, ?, ?, ?, ?)";
        final String[] statiValidi = {"LETTO", "NON LETTO", "IN LETTURA"};

        for (int i = 0; i < parametri.length; i++) {
            parametri[i] = parametri[i].trim();
        }

        if (parametri.length != 6) {
            throw new IllegalArgumentException("Numero di parametri non valido. Attesi 6 parametri.");
        }

        String isbn = parametri[2];
        if (isbn.isEmpty()) {
            throw new IllegalArgumentException("Il campo ISBN non può essere vuoto.");
        }

        int valutazione;
        try {
            valutazione = Integer.parseInt(parametri[4]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La valutazione deve essere un numero intero.");
        }
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException("Valutazione non valida: deve essere compresa tra 1 e 5.");
        }

        String statoLettura = parametri[5].toUpperCase();
        if (!Arrays.asList(statiValidi).contains(statoLettura)) {
            throw new IllegalArgumentException("Stato lettura non valido. Valori ammessi: " + Arrays.toString(statiValidi));
        }

        return query;
    }
}
