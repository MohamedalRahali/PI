package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private void navigateToManageProduit(ActionEvent event) throws IOException {
        loadScene("/com/wings/pi_java/manage_produit.fxml", event);
    }

    @FXML
    private void navigateToManageCategorie(ActionEvent event) throws IOException {
        loadScene("/com/wings/pi_java/manage_categorie.fxml", event);
    }

    @FXML
    public void navigateToAddProduit(ActionEvent event) throws IOException {
        loadScene("/com/wings/pi_java/add_produit.fxml", event);
    }

    @FXML
    public void navigateToAddCategorie(ActionEvent event) throws IOException {
        loadScene("/com/wings/pi_java/add_categorie.fxml", event);
    }

    @FXML
    private void navigateToPanier(ActionEvent event) throws IOException {
        loadScene("/com/wings/pi_java/panier.fxml", event);
    }

    private void loadScene(String fxmlPath, ActionEvent event) throws IOException {
        try {
            // Charge le FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Crée la scène
            Scene scene = new Scene(root);

            // Charge le CSS (chemin absolu depuis la racine des ressources)
            URL cssUrl = getClass().getResource("/com/wings/pi_java/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS file not found at: /com/wings/pi_java/styles.css");
            }

            // Configure la fenêtre
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
            throw e;
        }
    }
}