package com.progettoids_giuseppedemarco.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private static volatile DatabaseConnectionManager instance;
    private static final String CONFIG_FILE = "db.properties";

    private String url;
    private String username;
    private String password;

    private DatabaseConnectionManager() {
        loadConfiguration();
    }

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionManager.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public synchronized void configure(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection openConnection() throws SQLException {
        if (url == null || username == null) {
            throw new IllegalStateException("configura prima la connessione DB");
        }
        return DriverManager.getConnection(url, username, password);
    }

    private void loadConfiguration() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("file di configurazione DB non trovato: " + CONFIG_FILE);
            }

            properties.load(inputStream);
            this.url = readRequiredProperty(properties, "db.url");
            this.username = readRequiredProperty(properties, "db.username");
            this.password = properties.getProperty("db.password", "");
        } catch (IOException e) {
            throw new IllegalStateException("impossibile leggere la configurazione DB", e);
        }
    }

    private String readRequiredProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("proprieta DB mancante: " + key);
        }
        return value;
    }
}
