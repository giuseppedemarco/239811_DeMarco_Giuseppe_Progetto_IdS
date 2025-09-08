package src.main.java.ui.CommandButtons;

import javax.swing.JOptionPane;

import src.main.java.commands.cmd.UpdateCommand;
import src.main.java.utils.builders.UpdateCommandBuilder;

public class UpdateButton extends CommandButton{
    public UpdateButton() {
        super(
            "Inserisci l'aggiornamento del libro:\n\n" +
            "Seleziona il campo da modificare:\n" +
            "  1 - Genere\n" +
            "  2 - Valutazione\n" +
            "  3 - Stato di lettura\n\n" +
            "Formato dell'input:\n" +
            "  campo, nuovo valore, ISBN\n\n" +
            "Esempio:\n" +
            "  1, Giallo, 1234-5678\n\n" +
            "Nota: assicurati che l'ISBN sia corretto!"
        );
    }

    /*
     * params[0] indica il campo da aggiornare: 1=genere, 2=valutazione, 3=stato lettura.
     * Grazie allo switch case siamo in grado di selezionare il metodo corretto nel builder
     * per la costruzione della nostra query di UPDATE
     */
    @Override
    protected void esegui() {
        try {
            String[] params = getInput().getText().split(",");
            for (int i = 0; i < params.length; i++) params[i] = params[i].trim();

            UpdateCommandBuilder builder = new UpdateCommandBuilder();
            UpdateCommand command;

            switch (Integer.parseInt(params[0])) {
                case 1:
                    command = builder.updateGenre(params[1], params[2]).build();
                    break;
                case 2:
                    command = builder.updateReview(params[1], params[2]).build();
                    break;
                case 3:
                    command = builder.updateState(params[1], params[2]).build();
                    break;
                default:
                    throw new IllegalArgumentException("Campo non valido: " + params[0]);
            }

            command.exec();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Errore durante l'esecuzione del comando: " + e.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}
