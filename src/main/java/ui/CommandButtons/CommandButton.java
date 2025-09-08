package src.main.java.ui.CommandButtons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public abstract class CommandButton extends JFrame{
    private static final Color COLORE = new Color(249, 246, 238);
    private final JTextField input = new JTextField(30);
    private final JButton invia = new JButton("Invia");

    public CommandButton(String ist) {
        setTitle("Inserisci parametri");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(COLORE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        add(initIstruzioni(ist), gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(input, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        add(invia, gbc);
        invia.addActionListener(e -> esegui());
        pack();
        setMinimumSize(new Dimension(400, 150));
        setVisible(true);
        setLocationRelativeTo(null);
    }


    public JTextField getInput() {
        return input;
    }

    protected JPanel initIstruzioni(String i) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(249, 246, 238));
        JTextArea istruzioni = new JTextArea(i);
        istruzioni.setEditable(false);
        istruzioni.setBackground(null);
        istruzioni.setFont(new Font("Arial", Font.PLAIN, 14));
        istruzioni.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(istruzioni);
        return panel;
    }

    protected abstract void esegui();

}
