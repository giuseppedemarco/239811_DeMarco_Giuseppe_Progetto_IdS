package src.main.java.ui.concreteFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import src.main.java.commands.cmd.ExportCommand;
import src.main.java.ui.AbstractFrame;
import src.main.java.ui.CommandButtons.CreateButton;
import src.main.java.ui.CommandButtons.DeleteButton;
import src.main.java.ui.CommandButtons.FindButton;
import src.main.java.ui.CommandButtons.SortButton;
import src.main.java.ui.CommandButtons.UpdateButton;
import src.main.java.ui.components.RoundedButton;
import src.main.java.ui.components.SimpleButton;
import src.main.java.utils.builders.ExportCommandBuilder;

public class InsertBookForm extends AbstractFrame {


    private RoundedButton ins;
    private RoundedButton del;
    private RoundedButton upd;
    private RoundedButton cer;
    private RoundedButton ord;

    private final JTextArea infoText = new JTextArea( "Controllare la console di Java per eventuali errori.\n" );
    private JPanel buttonPanel;
    private JPanel layout;

    public InsertBookForm(){
        super();
        initializeFormComponents();
        init();
    }

    private void initializeFormComponents(){
        ins = new RoundedButton(new SimpleButton("Aggiungi un libro"), "#8686AC");
        del = new RoundedButton(new SimpleButton("Elimina un libro"), "#8686AC");
        upd = new RoundedButton(new SimpleButton("Modifica un libro"), "#8686AC");
        cer = new RoundedButton(new SimpleButton("Cerca"), "#8686AC");
        ord = new RoundedButton(new SimpleButton("Ordina"), "#8686AC");
        layout = new JPanel();
        layout.setBackground(Color.decode("#272757"));
        getContentPane().setBackground(Color.decode("#272757"));
    
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(ins.getButton());
        buttonPanel.add(del.getButton());
        buttonPanel.add(upd.getButton());
        buttonPanel.add(cer.getButton());
        buttonPanel.add(ord.getButton());
    }

    @Override
    protected void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExportCommand exp = new ExportCommandBuilder().create();
                exp.exec();
                dispose();
            }
        });
        componentManager();
        layoutManager();
        add(layout);
    }

    @Override
    protected void componentManager() {
        infoText.setEditable(false);
        infoText.setFocusable(false);
        infoText.setBorder(null);
        infoText.setOpaque(false);
        infoText.setBackground(new Color(0,0,0,0));
        infoText.setFont(new Font("Montserrat", Font.BOLD, 32));
        infoText.setForeground(Color.WHITE);
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        componentActionListener();
    }

    protected void componentActionListener(){
        ins.getButton().addActionListener(e -> {
            CreateButton createButton = new CreateButton();
        });
        upd.getButton().addActionListener(e -> {
            UpdateButton updateButton = new UpdateButton();
        });
        del.getButton().addActionListener(e -> {
            DeleteButton deleteButton = new DeleteButton();
        });
        cer.getButton().addActionListener(e -> {
            FindButton findButton = new FindButton();
        });
        ord.getButton().addActionListener(e -> {
            SortButton sortButton = new SortButton();
        });
    }

    @Override
    protected void layoutManager() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#272757"));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(infoText);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);

        getContentPane().add(mainPanel);
    }


}
