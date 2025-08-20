package src.main.java.ui.concreteFrames;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.main.java.ui.AbstractFrame;

public class Welcome extends AbstractFrame{
    private JButton welcomeButton;
    private JLabel welcomeText;
    private JPanel layout;

    public Welcome(){
        super();
        initializeWelcomePageComponents();
        init();
    }

    private void initializeWelcomePageComponents(){
        welcomeButton = new JButton("Entra");
        welcomeText = new JLabel("Benvenuto");
        layout = new JPanel();
    }


    @Override
    protected void init() {
        componentManager();
        layoutManager();
        add(layout);
    }

    @Override
    protected void componentManager() {
        layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeText.setAlignmentY(Component.CENTER_ALIGNMENT);
        welcomeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        componentActionListener();
    }

    private void componentActionListener(){
        //TODO
        welcomeButton.addActionListener(null);
    }

    @Override
    protected void layoutManager() {
        layout.add(Box.createVerticalGlue());
        layout.add(welcomeText);
        layout.add(Box.createRigidArea(new Dimension(0,10)));
        layout.add(welcomeButton);
        layout.add(Box.createHorizontalGlue());
    }

    
}
