package src.main.java.utils.builders;

import src.main.java.commands.cmd.FindCommand;
import src.main.java.utils.Fields;

public class FindCommandBuilder {
    /**
     * Crea un oggetto {@link FindCommand} basato sul campo selezionato e sul valore di ricerca.
     *
     * @param field il campo della tabella da usare per la ricerca
     * @param hook il valore da cercare (verrà usato con LIKE)
     * @return un'istanza di {@link FindCommand} pronta per l'esecuzione
     */
    public FindCommand create(Fields field, String hook) {
        String q = fieldToQuery(field);
        return new FindCommand(q, hook);
    }

    /**
     * Genera la query basata sul campo selezionato.
     *
     * @param field il campo della tabella da usare per la ricerca
     * @return la query corrispondente
     */
    private String fieldToQuery(Fields field) {
        return "SELECT * FROM libreria WHERE " + field.getColumn() + " LIKE ?";
    }
}
