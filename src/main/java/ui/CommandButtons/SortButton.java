package src.main.java.ui.CommandButtons;

import javax.swing.JOptionPane;

import src.main.java.commands.cmd.SortCommand;
import src.main.java.utils.Fields;
import src.main.java.utils.builders.SortCommandBuilder;

public class SortButton extends CommandButton{
    public SortButton() {
        super(
            "Ordinamento libri:\n\n" +
            "Seleziona il campo e l'ordine di ordinamento:\n" +
            "  1 - Nome (ASC/DESC)\n" +
            "  2 - Autore (ASC/DESC)\n" +
            "  3 - ISBN (ASC/DESC)\n" +
            "  4 - Genere (ASC/DESC)\n" +
            "  5 - Valutazione (ASC/DESC)\n\n" +
            "Formato dell'input:\n" +
            "  campo, ordine\n\n" +
            "Esempio:\n" +
            "  1, ASC\n" +
            "Nota: usare ASC per ordine crescente o DESC per decrescente."
        );
    }

    /*
    * params[0] indica il campo su cui ordinare: 1=Nome, 2=Autore, 3=ISBN, 4=Genere, 5=Valutazione.
    * params[1] indica l'ordine: ASC per crescente, DESC per decrescente.
    * Grazie allo switch case selezioniamo il campo corretto nell'enum Fields e configuriamo
    * il builder per creare il comando di ordinamento.
    */
    @Override
    protected void esegui() {
        try {
            String[] params = getInput().getText().split("[\\s,]+");
            if (params.length < 2) {
                throw new IllegalArgumentException("Inserire sia il campo che l'ordine (ASC/DESC).");
            }

            int fieldNum = Integer.parseInt(params[0]);
            String order = params[1].toUpperCase();

            SortCommandBuilder builder = new SortCommandBuilder();

            switch (fieldNum) {
                case 1: builder.byField(Fields.NOME); break;
                case 2: builder.byField(Fields.AUTORE); break;
                case 3: builder.byField(Fields.ISBN); break;
                case 4: builder.byField(Fields.GENERE); break;
                case 5: builder.byField(Fields.VALUTAZIONE); break;
                default: throw new IllegalArgumentException("Campo non valido: " + fieldNum);
            }

            if ("ASC".equals(order)) {
                builder.ascending();
            } else if ("DESC".equals(order)) {
                builder.descending();
            } else {
                throw new IllegalArgumentException("Ordine non valido: " + order);
            }

            SortCommand command = builder.build();
            command.exec();
            dispose();

        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Errore durante l'esecuzione del comando: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}
