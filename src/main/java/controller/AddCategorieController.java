package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Categorie;
import Services.CategorieService;
import Services.AIService;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.regex.Pattern;

public class AddCategorieController implements Initializable {

    @FXML private TextField libelleField;
    @FXML private TextArea descriptionField;
    @FXML private Button generateDescriptionButton;

    private Categorie selectedCategorie;
    private final CategorieService categorieService = new CategorieService();
    private final AIService aiService = new AIService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidators();

        // Ajout du listener pour la génération automatique de la description
        libelleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.equals(oldValue)) {
                genererDescriptionCategorie(newValue);
            }
        });
    }

    private void genererDescriptionCategorie(String libelle) {
        System.out.println("Génération de la description pour: " + libelle);
        new Thread(() -> {
            String description = aiService.generateCategoryDescription(libelle);
            System.out.println("Description générée: " + description);
            Platform.runLater(() -> {
                descriptionField.setText(description);
                System.out.println("Description mise à jour dans le champ");
            });
        }).start();
    }

    private void setupValidators() {
        // Validateur pour le libellé (lettres, chiffres et espaces uniquement)
        libelleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9\\s]*")) {
                libelleField.setText(oldValue);
            }
        });

        // Suppression temporaire du validateur de la description
        // descriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
        //     if (!newValue.matches("[a-zA-Z0-9\\s.,!?-]*")) {
        //         descriptionField.setText(oldValue);
        //     }
        // });
    }

    public void setCategorie(Categorie categorie) {
        this.selectedCategorie = categorie;
        if (categorie != null) {
            libelleField.setText(categorie.getLibelle());
            descriptionField.setText(categorie.getDescription());
        }
    }

    @FXML
    private void handleSaveCategorie(ActionEvent event) {
        try {
            if (!validateFields()) return;

            if (selectedCategorie == null) {
                selectedCategorie = new Categorie();
            }

            selectedCategorie.setLibelle(libelleField.getText().trim());
            selectedCategorie.setDescription(descriptionField.getText().trim());

            categorieService.save(selectedCategorie);
            showAlert("Succès", "Catégorie enregistrée avec succès", Alert.AlertType.INFORMATION);
            closeWindow(event);

        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la sauvegarde", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        StringBuilder errors = new StringBuilder();

        if (libelleField.getText().trim().isEmpty()) {
            errors.append("- Le libellé est obligatoire\n");
        } else if (libelleField.getText().trim().length() < 3) {
            errors.append("- Le libellé doit contenir au moins 3 caractères\n");
        }

        if (descriptionField.getText().trim().isEmpty()) {
            errors.append("- La description est obligatoire\n");
        } else if (descriptionField.getText().trim().length() < 10) {
            errors.append("- La description doit contenir au moins 10 caractères\n");
        }

        if (errors.length() > 0) {
            showAlert("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    @FXML
    private void handleBackToListCategorie(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void handleBackToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wings/pi_java/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleGenerateDescription(ActionEvent event) {
        String libelle = libelleField.getText();
        if (libelle == null || libelle.trim().isEmpty()) {
            showAlert("Erreur", "Veuillez d'abord saisir un libellé pour la catégorie.", Alert.AlertType.ERROR);
            return;
        }

        // Désactiver le bouton pendant la génération
        generateDescriptionButton.setDisable(true);

        // Exécuter la génération dans un thread séparé
        new Thread(() -> {
            try {
                String description = aiService.generateCategoryDescription(libelle);
                Platform.runLater(() -> {
                    descriptionField.setText(description);
                    generateDescriptionButton.setDisable(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    generateDescriptionButton.setDisable(false);
                });
            }
        }).start();
    }

    private void closeWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}