package src.main.java.ui.components;
import java.awt.*;
import javax.swing.*;

public class RoundedButton extends ButtonDecorator{
    
    public RoundedButton(ButtonComponent decoratedButton, String color) {
        super(decoratedButton);
        styleButton(getButton(), color);
    }

    private void styleButton(JButton button, String color) {
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode(color));
        button.setFont(new Font("Montserrat", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
    }
}
