package src.main.java.ui;

import javax.swing.JFrame;
import java.awt.*;

public abstract class AbstractFrame extends JFrame {
    private static final int WIDTH = 1080;
    private static final int LENGTH = 1920;
    private static final Color COLOR = new Color(101, 67, 33);
    private static final String TITLE = "Gestore di Libreria Personale";

    public AbstractFrame(){
        super(TITLE);
        setSize(LENGTH, WIDTH);
        setVisible(true);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    protected abstract void init();
    protected abstract void componentManager();
    protected abstract void layoutManager();

}
