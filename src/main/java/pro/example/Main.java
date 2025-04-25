package pro.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger l'interface principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/main.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestion des Produits et Catégories");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void debugResources() {
        try {
            System.out.println("=== DEBUG RESSOURCES ===");
            System.out.println("CSS: " + getClass().getResource("/com/wings/pi_java/styles.css"));
            System.out.println("Image: " + getClass().getResource("/com/wings/pi_java/images/palette.png"));

            // Liste le contenu du dossier images
            URL imagesDir = getClass().getResource("/com/wings/pi_java/images/");
            if (imagesDir != null) {
                System.out.println("Contenu images: " +
                        String.join(", ", new File(imagesDir.toURI()).list()));
            } else {
                System.out.println("Dossier images non trouvé");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Initialise JavaFX manuellement
        System.setProperty("javafx.version", "17.0.2");
        System.setProperty("javafx.verbose", "true");
        launch(args);
    }
}

