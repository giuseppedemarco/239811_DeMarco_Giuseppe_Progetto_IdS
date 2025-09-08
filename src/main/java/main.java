package src.main.java;

import java.sql.Connection;

import src.main.java.db.ConnectionDB;
import src.main.java.ui.concreteFrames.Home;


class main{
    public static void main(String...args){
        ConnectionDB db = new ConnectionDB();
        Connection connessione = db.getConnection();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            new ConnectionDB().closeConnection(connessione);
        }));
        Home home = new Home();
        
    }
}