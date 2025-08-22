package src.main.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private Connection connection;

    private String url;
    private String user;
    private String pwd;

    public ConnectionDB(){
        initializeDBCredentials();
        try{
            connection = DriverManager.getConnection(url, user, pwd);
            connection.setAutoCommit(true);
        }catch(SQLException e){
            System.out.println("Si è verificato un errore durante la connessione.");
        }
    }

    private void initializeDBCredentials(){
        url = "jdbc:mysql://localhost:3306/libreria";
        user = "root";
        pwd = "root";
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try{
            if(connection != null){
                connection.close();
            }
        }catch(SQLException e){
            System.out.println("Errore nella chiusura della connessione con il DB:");
        }
    }
    
}
