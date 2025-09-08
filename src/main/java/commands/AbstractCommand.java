package src.main.java.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.java.db.ConnectionDB;

public abstract class AbstractCommand implements Command{
    private static int count = 1;
    private final int numeroIst;
    private String query;

    public AbstractCommand(String q){
        this.numeroIst = count;
        this.query = q;
        count++;
    }

    public String toString(){
        return numeroIst + "-"+query;
    }

    public PreparedStatement createStatement() throws SQLException {
        return new ConnectionDB().getConnection().prepareStatement(query);
    }

    public abstract void exec();
}
