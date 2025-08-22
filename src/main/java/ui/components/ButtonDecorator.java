package src.main.java.ui.components;

import javax.swing.JButton;

public abstract class ButtonDecorator implements ButtonComponent{
    protected ButtonComponent decoratedButton;

    public ButtonDecorator(ButtonComponent button){
        decoratedButton = button; 
    }

    @Override
    public JButton getButton() {
        return decoratedButton.getButton();
    }
}
