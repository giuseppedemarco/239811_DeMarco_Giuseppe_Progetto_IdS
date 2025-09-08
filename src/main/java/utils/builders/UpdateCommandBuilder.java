package src.main.java.utils.builders;

import src.main.java.commands.cmd.UpdateCommand;

public class UpdateCommandBuilder {

    private String query;
    private String newValue;
    private String isbn;

    /**
     * Imposta la query per aggiornare il genere di un libro dato il codice ISBN.
     *
     * @param nGenre il nuovo genere da assegnare
     * @param isbn il codice ISBN del libro da aggiornare
     * @return l'istanza corrente del builder per consentire chiamate fluenti
     */
    public UpdateCommandBuilder updateGenre(String nGenre, String isbn) {
        this.query = "UPDATE libreria SET genere = ? WHERE isbn = ?";
        this.newValue = nGenre;
        this.isbn = isbn;
        return this;
    }

    /**
     * Imposta la query per aggiornare la valutazione di un libro dato il codice ISBN.
     *
     * @param review la nuova valutazione da assegnare
     * @param isbn il codice ISBN del libro da aggiornare
     * @return l'istanza corrente del builder per consentire chiamate fluenti
     */
    public UpdateCommandBuilder updateReview(String review, String isbn) {
        this.query = "UPDATE libreria SET valutazione = ? WHERE isbn = ?";
        this.newValue = review;
        this.isbn = isbn;
        return this;
    }

    /**
     * Imposta la query per aggiornare lo stato di lettura di un libro dato il codice ISBN.
     *
     * @param state il nuovo stato di lettura da assegnare
     * @param isbn il codice ISBN del libro da aggiornare
     * @return l'istanza corrente del builder per consentire chiamate fluenti
     */
    public UpdateCommandBuilder updateState(String state, String isbn) {
        this.query = "UPDATE libreria SET statoLettura = ? WHERE isbn = ?";
        this.newValue = state;
        this.isbn = isbn;
        return this;
    }

    /**
     * Costruisce l'oggetto {@link UpdateCommand} sulla base dei parametri impostati in precedenza.
     * Verifica che tutti i campi necessari siano stati impostati, altrimenti lancia {@link IllegalStateException}.
     *
     * @return un'istanza di {@link UpdateCommand} pronta per l'esecuzione
     * @throws IllegalStateException se il builder non è stato configurato correttamente
     */
    public UpdateCommand build() {
        if (query == null || newValue == null || isbn == null) {
            throw new IllegalStateException("Configurazione del Builder fallita!");
        }
        return new UpdateCommand(query, newValue, isbn);
    }
}
