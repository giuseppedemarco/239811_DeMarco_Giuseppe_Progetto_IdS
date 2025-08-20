package src.main.java.ui.concreteFrames;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InsertBookForm extends JFrame implements ActionListener{

    private Container c;
    private JLabel frameTitle;
    private JLabel bookName;
    private JTextField tBookName;
    private JLabel autorName;
    private JTextField tAutorName;
    private JLabel ISBN;
    private JTextField tISBN;
    private JLabel genre;
    private JTextField tGenre;
    private JLabel review;
    private JTextField tReview;
    private JLabel readingProgress;
    private JTextField tReadingProgress;
    private JButton submit;
    private JButton reset;

    public InsertBookForm(){
        setTitle("Aggiungi libro alla collezione");
        setBounds(300,90,900,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        initializeFormComponents();
        setVisible(true);
    }

    private void initializeFormComponents(){
        frameTitle = new JLabel("Inserimento libro");
        frameTitle.setSize(300, 30);
        frameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        frameTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        c.add(frameTitle);

        bookName = new JLabel("Nome libro");
        bookName.setSize(100, 20);
        bookName.setLocation(100,100);
        c.add(bookName);

        tBookName = new JTextField();
        tBookName.setSize(190,20);
        tBookName.setLocation(200,100);
        c.add(bookName);

        autorName = new JLabel("Nome libro");
        autorName.setSize(100, 20);
        autorName.setLocation(100,150);
        c.add(autorName);

        tAutorName = new JTextField();
        tAutorName.setSize(150,20);
        tAutorName.setLocation(200,150);
        c.add(tAutorName);

        ISBN = new JLabel("ISBN");
        ISBN.setSize(100, 20);
        ISBN.setLocation(100,200);
        c.add(ISBN);

        tISBN = new JTextField();
        tISBN.setSize(190,20);
        tISBN.setLocation(200,200);
        c.add(tISBN);

        genre = new JLabel("Genere");
        genre.setSize(100, 20);
        genre.setLocation(100,250);
        c.add(genre);

        tGenre = new JTextField();
        tGenre.setSize(190,20);
        tGenre.setLocation(250,250);
        c.add(tGenre);

        review = new JLabel("Recensione");
        review.setSize(100, 20);
        review.setLocation(100,300);
        c.add(review);

        tReview = new JTextField();
        tReview.setSize(190,20);
        tReview.setLocation(300,300);
        c.add(tReview);

        readingProgress = new JLabel("Stato di lettura");
        readingProgress.setSize(100, 20);
        readingProgress.setLocation(100,350);
        c.add(readingProgress);

        tReadingProgress = new JTextField();
        tReadingProgress.setSize(190,20);
        tReadingProgress.setLocation(350,350);
        c.add(tReadingProgress);

        submit = new JButton("Aggiungi");
        submit.setSize(100, 20);
        submit.setLocation(150, 450);
        submit.addActionListener(this);
        c.add(submit);

        reset = new JButton("Reset");
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(this);
        c.add(reset);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            //TODO
        }else if(e.getSource() == reset){
            //TODO
        }
    }
    
}
