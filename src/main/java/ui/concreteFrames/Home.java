package src.main.java.ui.concreteFrames;

import java.awt.*;
import javax.swing.*;
import src.main.java.ui.AbstractFrame;
import src.main.java.ui.components.RoundedButton;
import src.main.java.ui.components.SimpleButton;

public class Home extends AbstractFrame{

    private RoundedButton createButton;
    private RoundedButton uploadButton;
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
        frameSubTitle = new JLabel("Crea la tua libreria o caricane una per cominciare!");
        uploadButton = new RoundedButton(new SimpleButton("CARICA"), "#8686AC");
        createButton = new RoundedButton(new SimpleButton("CREA"), "#505081");
        layout = new JPanel();
        layout.setBackground(Color.decode("#272757"));
        getContentPane().setBackground(Color.decode("#272757"));
    
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createButton.getButton());
        buttonPanel.add(uploadButton.getButton());
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

        uploadButton.getButton().setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.getButton().setAlignmentY(Component.CENTER_ALIGNMENT);
        createButton.getButton().setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.getButton().setAlignmentY(Component.CENTER_ALIGNMENT);
        
        componentActionListener();
    }

    private void componentActionListener(){
        //TODO
        createButton.getButton().addActionListener(null);
        //TODO
        uploadButton.getButton().addActionListener(null);
    }

    @Override
    protected void layoutManager() {
        layout.add(frameTitle);
        layout.add(Box.createRigidArea(new Dimension(10, 20)));
        layout.add(frameSubTitle);
        layout.add(buttonPanel);
    }
}
