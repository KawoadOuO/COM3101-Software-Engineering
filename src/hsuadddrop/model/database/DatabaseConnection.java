package hsuadddrop.model.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private Connection connection;

    // flag for first time setup
    private boolean firstTimeSetup = false;

    private DatabaseConnection() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // check db file exists
            File dbFile = new File("./adddrop.db");
            if (!dbFile.exists()) {
                firstTimeSetup = true;
            }
            // Connect to the SQLite database file
            String url = "jdbc:sqlite:./adddrop.db";
            connection = DriverManager.getConnection(url);
            // check table exists
            if (!connection.getMetaData().getTables(null, null, "student", null).next()) {
                firstTimeSetup = true;
            }
            if (firstTimeSetup) {
                System.out.println("database not found, creating new database");
                // get setup sql file
                File setupFile = new File("setup.sql");
                // check setup file exists
                if (!setupFile.exists()) {
                    throw new FileNotFoundException("setup.sql not found");
                }
                // read sql file using scanner
                Scanner scanner = new Scanner(setupFile);
                scanner.useDelimiter(";");
                while (scanner.hasNext()) {
                    String sql = scanner.next();
                    connection.prepareStatement(sql).execute();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
