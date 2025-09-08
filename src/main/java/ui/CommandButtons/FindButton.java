package src.main.java.ui.CommandButtons;

import javax.swing.JOptionPane;

import src.main.java.commands.cmd.FindCommand;
import src.main.java.utils.Fields;
import src.main.java.utils.builders.FindCommandBuilder;

public class FindButton extends CommandButton{

    public FindButton() {
        super(
            "Ricerca libri:\n\n" +
            "Seleziona il campo da cercare:\n" +
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
    * params[0] indica il campo su cui effettuare la ricerca: 
    * 1=nome, 2=autore, 3=ISBN, 4=genere, 5=valutazione, 6=stato lettura.
    * params[1] è il valore da cercare.
    * Grazie allo switch case selezioniamo il campo corretto nell'enum Fields
    * e costruiamo il FindCommand tramite il builder.
    */
    @Override
    protected void esegui() {
        try {
            String[] params = getInput().getText().split("[,]+");
            for (int i = 0; i < params.length; i++) params[i] = params[i].trim();

            int fieldNum = Integer.parseInt(params[0]);
            String value = params[1];

            FindCommandBuilder builder = new FindCommandBuilder();
            FindCommand command;

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
            JOptionPane.showMessageDialog(this,"Errore durante l'esecuzione del comando: " + e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
        }
    }
}
