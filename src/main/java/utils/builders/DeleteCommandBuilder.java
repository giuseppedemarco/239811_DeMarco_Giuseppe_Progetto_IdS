package src.main.java.utils.builders;

import src.main.java.commands.cmd.DeleteCommand;
import src.main.java.utils.Fields;
public class DeleteCommandBuilder {
    /**
     * Crea un oggetto {@link DeleteCommand} basato sul campo selezionato e sul valore di confronto.
     *
     * @param field il campo della tabella su cui effettuare la cancellazione
     * @param hook il valore da confrontare (sarà usato con =)
     * @return un'istanza di {@link DeleteCommand} pronta per l'esecuzione
     */
    public DeleteCommand create(Fields field, String hook) {
        String q = fieldToQuery(field);
        return new DeleteCommand(q, hook);
    }

    /**
     * Genera la query SQL basata sul campo selezionato.
     *
     * @param field il campo della tabella
     * @return la query SQL corrispondente
     */
    private String fieldToQuery(Fields field) {
        String query = "DELETE FROM libreria WHERE " + field.getColumn();
        if (field.isIgnoreCase()) {
            query += " COLLATE NOCASE";
        }
        query += " = ?";
        return query;
    }
}
