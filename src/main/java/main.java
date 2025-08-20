package src.main.java;

import java.sql.Connection;

import src.main.java.db.ConnectionDB;
import src.main.java.ui.concreteFrames.Welcome;

class main{
    public static void main(String...args){
        ConnectionDB db = new ConnectionDB();
        Connection connessione = db.getConnection();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConnectionDB.getIstance().closeConnection();
        }));
        Welcome welcomePage = new Welcome();
    }
}