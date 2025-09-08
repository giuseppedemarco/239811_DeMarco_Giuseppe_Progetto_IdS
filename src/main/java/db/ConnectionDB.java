package src.main.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String DB_PATH = "miaLibreria.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database:");
            e.printStackTrace();
            throw new RuntimeException("Impossibile connettersi al database SQLite.", e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la chiusura della connessione:");
            e.printStackTrace();
        }
    }
}
