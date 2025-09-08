package src.main.java.utils.builders;

import src.main.java.commands.cmd.SortCommand;
import src.main.java.utils.Fields;

public class SortCommandBuilder {

    private String field;
    private boolean ascending = true;

    /**
     * Imposta il campo su cui verrà effettuato l'ordinamento della query.
     * 
     * Accetta come parametro un valore dell'enum {@link Fields}, 
     * 
     * @param field il campo della tabella da usare per l'ordinamento
     * @return l'istanza corrente di {@code SortCommandBuilder}, per consentire chiamate fluente
     */
    public SortCommandBuilder byField(Fields field) {
        this.field = field.getColumn();
        return this;
    }

    /**
     * Imposta l'ordinamento della tabella in modo ASCENDENTE.
     * @return l'istanza corrente di {@code SortCommandBuilder}, per consentire chiamate fluente
     */
    public SortCommandBuilder ascending() {
        this.ascending = true;
        return this;
    }

    /**
     * Imposta l'ordinamento della tabella in modo DISCENDENTE.
     * @return l'istanza corrente di {@code SortCommandBuilder}, per consentire chiamate fluente
     */
    public SortCommandBuilder descending() {
        this.ascending = false;
        return this;
    }

    /**
     * Costruisce la query SQL completa basata sul campo e sull'ordine selezionati in precedenza.
     * Se {@code ascending} è {@code true}, significa che è stato richiamato il metodo {@link #ascending()},
     * quindi alla query verrà aggiunta la clausola {@code ASC} per indicare l'ordinamento crescente.
     * Se {@code ascending} è {@code false}, significa che è stato richiamato il metodo {@link #descending()},
     * quindi alla query verrà aggiunta la clausola {@code DESC} per indicare l'ordinamento decrescente.
     *
     * @return un'istanza di {@link SortCommand} contenente la query SQL completa
     * @throws IllegalStateException se il campo di ordinamento non è stato impostato
     */
    public SortCommand build() {
        if (field == null) {
            throw new IllegalStateException("Campo di ordinamento non impostato.");
        }
        String q = "SELECT * FROM libreria ORDER BY " + field + (ascending ? " ASC" : " DESC");
        return new SortCommand(q);
    }
}
