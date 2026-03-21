package com.progettoids_giuseppedemarco.ui.dashboard;

import com.progettoids_giuseppedemarco.domain.Film;
import com.progettoids_giuseppedemarco.domain.StatoVisione;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridLayout;

public final class FilmFormDialog {
    private FilmFormDialog() {
    }

    public static FilmFormData show(Component parent, Film film) {
        JTextField titoloField = new JTextField(film == null ? "" : film.getTitolo());
        JTextField registaNomeField = new JTextField(film == null ? "" : film.getRegista().getNome());
        JTextField registaCognomeField = new JTextField(film == null ? "" : safeText(film.getRegista().getCognome()));
        JTextField annoField = new JTextField(film == null ? "" : String.valueOf(film.getAnnoDiUscita()));
        JTextField genereField = new JTextField(film == null ? "" : film.getGenere().getNome());
        JTextField valutazioneField = new JTextField(film == null ? "" : String.valueOf(film.getValutazionePersonale()));
        JComboBox<StatoVisione> statoCombo = new JComboBox<>(StatoVisione.values());
        if (film != null) {
            statoCombo.setSelectedItem(film.getStatoVisione());
        }

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.add(new JLabel("Titolo"));
        formPanel.add(titoloField);
        formPanel.add(new JLabel("Nome regista"));
        formPanel.add(registaNomeField);
        formPanel.add(new JLabel("Cognome regista"));
        formPanel.add(registaCognomeField);
        formPanel.add(new JLabel("Anno di uscita"));
        formPanel.add(annoField);
        formPanel.add(new JLabel("Genere"));
        formPanel.add(genereField);
        formPanel.add(new JLabel("Valutazione (1-5)"));
        formPanel.add(valutazioneField);
        formPanel.add(new JLabel("Stato visione"));
        formPanel.add(statoCombo);

        int result = JOptionPane.showConfirmDialog(
                parent,
                formPanel,
                film == null ? "Nuovo film" : "Aggiorna film",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        return new FilmFormData(
                titoloField.getText(),
                registaNomeField.getText(),
                registaCognomeField.getText(),
                annoField.getText(),
                genereField.getText(),
                valutazioneField.getText(),
                (StatoVisione) statoCombo.getSelectedItem()
        );
    }

    private static String safeText(String value) {
        return value == null ? "" : value;
    }
}
