package src.main.java.commands.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.main.java.commands.AbstractCommand;

public class FindCommand extends AbstractCommand{

    private String hook;

    public FindCommand(String query, String hook){
        super(query);
        this.hook = "%" + hook + "%";
    }

    @Override
    public void exec() {
        try {
            PreparedStatement ps = super.createStatement();
            try {
                int nh = Integer.parseInt(hook.substring(1, 5));
                if (nh >= 1 && nh <= 5) ps.setInt(1, nh);
            } catch (NumberFormatException e) {
                ps.setString(1, hook);
            }
            ResultSet rs = ps.executeQuery();
            System.out.println("RICERCA -------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
            }
            System.out.println("--------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Esecuzione fallita.");
        }
    }
    
}
