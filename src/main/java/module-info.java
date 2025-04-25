module pi.java {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens controller to javafx.fxml;
    opens models to javafx.base;
    opens Services to javafx.base;
    opens pro.example to javafx.graphics;
    
    exports controller;
    exports models;
    exports Services;
    exports pro.example;
} 