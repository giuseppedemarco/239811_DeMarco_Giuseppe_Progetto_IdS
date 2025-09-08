package src.main.java.ui.CommandButtons;

import src.main.java.commands.cmd.CreateCommand;
import src.main.java.utils.builders.CreateCommandBuilder;

import javax.swing.*;

public class CreateButton extends CommandButton {

    public CreateButton() {
        super(
            "Creazione nuovo libro:\n\n" +
            "Inserisci i dati in ordine, separati da una virgola:\n" +
            "  nome, autore, isbn, genere, valutazione, stato lettura\n\n" +
            "Esempio:\n" +
            "  Il Nome del Libro, Mario Rossi, 1234-5678, Giallo, 5, LETTO"
        );
    }

    /*
    * params contiene i valori del nuovo libro in ordine:
    * nome, autore, ISBN, genere, valutazione, stato lettura.
    * Il builder verifica i parametri, costruisce il CreateCommand
    * e lo esegue per inserire il libro nella libreria.
    */
    @Override
    protected void esegui() {
        try {
            String[] params = getInput().getText().split(",");
            if (params.length != 6) {
                throw new IllegalArgumentException("Attesi 6 parametri: nome, autore, isbn, genere, valutazione, stato lettura.");
            }

            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].trim();
            }

            CreateCommandBuilder builder = new CreateCommandBuilder();
            CreateCommand command = builder.create(params);
            command.exec();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Errore durante l'esecuzione del comando: " + e.getMessage(), "Errore",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

