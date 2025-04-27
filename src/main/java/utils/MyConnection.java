package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static MyConnection instance;
    private Connection cnx;
    
    private final String URL = "jdbc:mysql://localhost:3306/pi_java";
    private final String USER = "root";
    private final String PASSWORD = "";
    
    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.err.println("Erreur de connexion: " + ex.getMessage());
        }
    }
    
    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }
    
    public Connection getCnx() {
        return cnx;
    }
} 