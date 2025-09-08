package src.main.java.commands.cmd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.java.commands.AbstractCommand;

public class UpdateCommand extends AbstractCommand{

    private String editedValue;
    private String isbn;

    public UpdateCommand(String query, String editedValue, String isbn) {
        super(query);
        this.editedValue = editedValue;
        this.isbn = isbn;
    }

    @Override
    public void exec() {
        try {
            PreparedStatement ps = super.createStatement();
            try {
                ps.setInt(1, Integer.parseInt(editedValue));
            } catch (NumberFormatException e) {
                ps.setString(1, editedValue);
            }
            ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Esecuzione fallita");
            e.printStackTrace();
        }
    }
    
}
