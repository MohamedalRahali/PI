package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class  MyConnection {

    private Connection connection;
    private static MyConnection instance;

    private final String URL = "jdbc:mysql://localhost:3306/pi_java";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private MyConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if(instance == null)
            instance = new MyConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection getCnx() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Reconnected to database");
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection: " + e.getMessage());
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Reconnected to database after error");
            } catch (SQLException ex) {
                System.out.println("Failed to reconnect: " + ex.getMessage());
            }
        }
        return connection;
    }
}

