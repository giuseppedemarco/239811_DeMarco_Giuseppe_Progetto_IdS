package src.main.java.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.java.db.ConnectionDB;

public abstract class AbstractCommand implements Command{
    private String query;

    public AbstractCommand(String q){
        this.query = q;
    }

    public PreparedStatement createStatement() throws SQLException {
        return new ConnectionDB().getConnection().prepareStatement(query);
    }

    public abstract void exec();
}
