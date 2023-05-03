package hsuadddrop.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
    public static void main(String[] args) throws ClassNotFoundException{
    DatabaseConnection();}
    private static DatabaseConnection instance = null;
    java.sql.Connection connection ;

    
    public static void DatabaseConnection() throws ClassNotFoundException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
    
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:mysql://localhost/mydatabase";
            //String url = "C:\Hang Seng\Year3-sem2\COM3101 Software Engineering\Lab\COM3101\src\COM31011.db";
            //String user = "user";
            //String password = "user";
            //connection = DriverManager.getConnection(url, user, password);\
            
            connection = DriverManager.getConnection(url);  
            statement = connection.createStatement();
            
            
                String sql = "CREATE TABLE staff (" +
                             "staff_id VARCHAR(10) PRIMARY KEY," +
                             "name VARCHAR(50) NOT NULL," +
                             "password VARCHAR(255) NOT NULL" +
                             ");";
                statement.execute(sql);
            System.out.println("connected");
        } catch (SQLException e) {
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
    public void closeDB(){
            try {
                    connection.close();
                } catch (SQLException sqlex) {
                    System.out.println("some error happenes");
                }
    
    }
    
}
