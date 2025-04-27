module pi.java {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.mail;
    
    // Google API dependencies - temporarily commented out
    // requires com.google.api.client;
    // requires com.google.oauth.client;
    // requires com.google.http.client;
    // requires com.google.http.client.jackson2;
    
    opens controller to javafx.fxml;
    opens models to javafx.base;
    opens Services to javafx.base;
    opens pro.example to javafx.fxml, javafx.graphics;
    
    exports controller;
    exports models;
    exports Services;
    exports pro.example;
} 