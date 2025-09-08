package src.main.java.commands.cmd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.java.commands.AbstractCommand;

public class DeleteCommand extends AbstractCommand{

    private String hook;

    public DeleteCommand(String query, String hook){
        super(query);
        this.hook = hook;
    }

    

    @Override
    public void exec() {
        try {
            PreparedStatement ps = super.createStatement();
            try {
                int nh = Integer.parseInt(hook);
                if (nh >= 1 && nh <= 5) ps.setInt(1, nh);
            } catch (NumberFormatException e) {
                ps.setString(1, hook);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Esecuzione fallita.");
            e.printStackTrace();
        }
    }
    
}
