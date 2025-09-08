package src.main.java.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import src.main.java.db.ConnectionDB;

public class CSVGenerator {
    public static void generate(String out) {
        String query = "SELECT * FROM libreria";
        try (
                Connection c = new ConnectionDB().getConnection();
                Statement state = c.createStatement();
                ResultSet rs = state.executeQuery(query)
        ) {
            File file = new File(out);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                boolean cFolder = parent.mkdirs();
                if (!cFolder) {
                    System.err.println("Creazione della cartella fallita: " + parent.getAbsolutePath());
                    return;
                }
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                ResultSetMetaData res = rs.getMetaData();
                int j = res.getColumnCount();
                for (int i = 1; i <= j; i++) {
                    writer.print(res.getColumnName(i));
                    if (i < j) writer.print(",");
                }
                writer.println();
                while (rs.next()) {
                    for (int i = 1; i <= j; i++) {
                        writer.print(rs.getString(i));
                        if (i < j) writer.print(",");
                    }
                    writer.println();
                }
                System.out.println("Creazione del file .csv completata correttamente: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Creazione del file .csv fallita!");
            e.printStackTrace();
        }
    }

}
