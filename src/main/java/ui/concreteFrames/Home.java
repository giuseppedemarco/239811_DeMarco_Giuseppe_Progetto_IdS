package src.main.java.ui.concreteFrames;

import java.awt.*;
import javax.swing.*;

import src.main.java.commands.cmd.ExportCommand;
import src.main.java.ui.AbstractFrame;
import src.main.java.ui.components.RoundedButton;
import src.main.java.ui.components.SimpleButton;
import src.main.java.utils.builders.ExportCommandBuilder;

public class Home extends AbstractFrame{

    private RoundedButton exportButton;
    private RoundedButton createButton;
    private JLabel frameTitle;
    private JLabel frameSubTitle;
    private JPanel layout;
    private JPanel buttonPanel;

    public Home(){
        super();
        initializeHomeComponents();
        init();
    }

    private void initializeHomeComponents(){
        frameTitle = new JLabel("BENVENUTO"); 
        frameSubTitle = new JLabel("Crea la tua libreria o esportane il contenuto!");
        createButton = new RoundedButton(new SimpleButton("CREA"), "#8686AC");
        exportButton = new RoundedButton(new SimpleButton("ESPORTA"), "#505081");
        layout = new JPanel();
        layout.setBackground(Color.decode("#272757"));
        getContentPane().setBackground(Color.decode("#272757"));
    
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(exportButton.getButton());
        buttonPanel.add(createButton.getButton());
    }

    @Override
    public void init() {
        componentManager();
        layoutManager();
        add(layout);
    }

    @Override
    protected void componentManager() {
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));

        frameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        frameTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        frameTitle.setFont(new Font("Montserrat", Font.BOLD, 64));
        frameTitle.setForeground(Color.WHITE);

        frameSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        frameSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        frameSubTitle.setFont(new Font("Montserrat", Font.BOLD, 20));
        frameSubTitle.setForeground(Color.WHITE);

        createButton.getButton().setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.getButton().setAlignmentY(Component.CENTER_ALIGNMENT);
        exportButton.getButton().setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.getButton().setAlignmentY(Component.CENTER_ALIGNMENT);
        
        componentActionListener();
    }

    protected void componentActionListener(){
        exportButton.getButton().addActionListener(e -> {
            ExportCommandBuilder ecb = new ExportCommandBuilder();
            ExportCommand c = ecb.create();
            c.exec();
        });

        createButton.getButton().addActionListener(e -> {
            InsertBookForm ibf = new InsertBookForm();
            dispose();
        });
    }

    @Override
    protected void layoutManager() {
        layout.add(frameTitle);
        layout.add(Box.createRigidArea(new Dimension(10, 20)));
        layout.add(frameSubTitle);
        layout.add(buttonPanel);
    }
}
