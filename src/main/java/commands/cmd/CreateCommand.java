package src.main.java.commands.cmd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.main.java.commands.AbstractCommand;
import src.main.java.db.ConnectionDB;

public class CreateCommand extends AbstractCommand{
    private String[] att;

    public CreateCommand(String q, String[] att){
        super(q);
        this.att = att;
    }

    @Override
    public void exec() {
        try{
            Connection c = new ConnectionDB().getConnection();
            String find = "SELECT * FROM libreria WHERE isbn = ?";
            PreparedStatement val = c.prepareStatement(find);
            val.setString(1, att[2]);
            ResultSet rs = val.executeQuery();
            if(rs.next()) throw new IllegalArgumentException("Questo codice ISBN è già presente!");
            else{
                PreparedStatement create = super.createStatement();
                for(int i = 0; i < att.length; i++){
                    if(i == 4) create.setInt(5, Integer.parseInt(att[i]));
                    else create.setString(i+1, att[i]);
                }
                create.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Funzione di creazione fallita.");
        }
    }
}
