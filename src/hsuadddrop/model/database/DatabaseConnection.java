package hsuadddrop.model.database;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // check db file exists
            File dbFile = new File("./adddrop.db");
            if (!dbFile.exists()) {
                System.err.println("Database file not found.");
                System.exit(1);
            }
//             Connect to the SQLite database file
            String url = "jdbc:sqlite:./adddrop.db";
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
