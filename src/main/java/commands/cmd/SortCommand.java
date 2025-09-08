package src.main.java.commands.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.main.java.commands.AbstractCommand;

public class SortCommand extends AbstractCommand{

    public SortCommand(String q){
        super(q);
    }

    public void exec() {
        try {
            PreparedStatement ps = createStatement();
            ResultSet rs = ps.executeQuery();
            System.out.println("ORDINAMENTO ----------------------------------------");
            while (rs.next()) {
                System.out.println((rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6)));
            }
            System.out.println("--------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Esecuzione fallita.");
            e.printStackTrace();
        }
    }
    
}
