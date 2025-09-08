package src.main.java.ui.CommandButtons;

import javax.swing.JOptionPane;

import src.main.java.commands.cmd.DeleteCommand;
import src.main.java.utils.Fields;
import src.main.java.utils.builders.DeleteCommandBuilder;

public class DeleteButton extends CommandButton{

    public DeleteButton() {
        super(
            "ATTENZIONE: le modifiche, tranner per ISBN,\n" +
            "eliminano TUTTI i libri che rispettano il criterio!\n\n" +
            "Seleziona il campo su cui effettuare la cancellazione:\n" +
            "  1 - Nome\n" +
            "  2 - Autore\n" +
            "  3 - ISBN\n" +
            "  4 - Genere\n" +
            "  5 - Valutazione\n" +
            "  6 - Stato di lettura\n\n" +
            "Formato dell'input:\n" +
            "  campo, valore\n\n" +
            "Esempio:\n" +
            "  2, Mario Rossi"
        );
    }


    /*
    * params[0] indica il campo su cui effettuare la cancellazione: 
    * 1=nome, 2=autore, 3=ISBN, 4=genere, 5=valutazione, 6=stato lettura.
    * params[1] è il valore da confrontare.
    * Grazie allo switch case selezioniamo il campo corretto nell'enum Fields
    * e costruiamo il DeleteCommand tramite il builder.
    */

    @Override
    protected void esegui() {
        try {
            String[] params = getInput().getText().split(",");
            for (int i = 0; i < params.length; i++) params[i] = params[i].trim();

            int fieldNum = Integer.parseInt(params[0]);
            String value = params[1];

            DeleteCommandBuilder builder = new DeleteCommandBuilder();
            DeleteCommand command;

            switch (fieldNum) {
                case 1: command = builder.create(Fields.NOME, value); break;
                case 2: command = builder.create(Fields.AUTORE, value); break;
                case 3: command = builder.create(Fields.ISBN, value); break;
                case 4: command = builder.create(Fields.GENERE, value); break;
                case 5: command = builder.create(Fields.VALUTAZIONE, value); break;
                case 6: command = builder.create(Fields.STATO_LETTURA, value); break;
                default: throw new IllegalArgumentException("Campo non valido: " + fieldNum);
            }

            command.exec();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Errore durante l'esecuzione del comando: " + e.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}
