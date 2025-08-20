package src.main.java.ui.concreteFrames;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.main.java.ui.AbstractFrame;

public class Home extends AbstractFrame{

    private JButton createButton;
    private JButton uploadButton;
    private JLabel frameTitle;
    private JLabel frameSubTitle;
    private JPanel layout;

    public Home(){
        super();
        initializeHomeComponents();
        init();
    }

    private void initializeHomeComponents(){
        createButton = new JButton("Crea una libreria");
        uploadButton = new JButton("Carica una libreria");
        frameTitle = new JLabel("Home");
        frameSubTitle = new JLabel("Crea la tua libreria o caricane una per cominciare!");
        layout = new JPanel();
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
        frameSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        frameSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        componentActionListener();
    }

    private void componentActionListener(){
        //TODO
        createButton.addActionListener(null);
        //TODO
        uploadButton.addActionListener(null);
    }

    @Override
    protected void layoutManager() {
        layout.add(frameTitle);
        layout.add(Box.createRigidArea(new Dimension(10, 20)));
        layout.add(frameSubTitle);
        layout.add(Box.createRigidArea(new Dimension(20, 20)));
        layout.add(createButton);
        layout.add(Box.createRigidArea(new Dimension(10, 10)));
        layout.add(uploadButton);
    }
    
    
}
