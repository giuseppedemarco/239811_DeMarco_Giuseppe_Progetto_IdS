package src.main.java.ui.components;

import javax.swing.JButton;

public class SimpleButton implements ButtonComponent{
    
    private JButton button;
    
    public SimpleButton(String text){
        button = new JButton(text);
    }

    @Override
    public JButton getButton() {
        return button;
    }
}
