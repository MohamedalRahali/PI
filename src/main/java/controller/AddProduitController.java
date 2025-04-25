package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Produit;
import models.Categorie;
import Services.ProduitService;
import Services.CategorieService;
import Services.AIService;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddProduitController implements Initializable {

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private TextField artisteIdField;
    @FXML private TextField prixField;
    @FXML private TextField statutField;
    @FXML private DatePicker dateCreationField;
    @FXML private ComboBox<Categorie> categorieComboBox;
    @FXML private TextField imageField;
    @FXML private Button generateDescriptionButton;

    private Produit selectedProduit;
    private final ProduitService produitService = new ProduitService();
    private final CategorieService categorieService = new CategorieService();
    private final AIService aiService = new AIService();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean canGenerate = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Categorie> categories = categorieService.getAll();
            categorieComboBox.setItems(FXCollections.observableArrayList(categories));

            categorieComboBox.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Categorie item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getLibelle());
                }
            });

            categorieComboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Categorie item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getLibelle());
                }
            });

            // Ajout des validateurs
            setupValidators();

            // Ajout du listener pour la génération automatique de la description basée sur l'image
            imageField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && !newValue.equals(oldValue)) {
                    genererDescriptionParImage(newValue);
                }
            });

            setupGenerateButton();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genererDescriptionParImage(String imageUrl) {
        new Thread(() -> {
            String description = aiService.genererDescriptionParImage(imageUrl);
            javafx.application.Platform.runLater(() -> {
                descriptionField.setText(description);
            });
        }).start();
    }

    private void setupValidators() {
        // Validateur pour le titre (lettres, chiffres et espaces uniquement)
        titreField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9\\s]*")) {
                titreField.setText(oldValue);
            }
        });

        // Validateur pour l'ID artiste (nombres uniquement)
        artisteIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                artisteIdField.setText(oldValue);
            }
        });

        // Validateur pour le prix (nombres avec point décimal)
        prixField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                prixField.setText(oldValue);
            }
        });

        // Validateur pour le statut (lettres uniquement)
        statutField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                statutField.setText(oldValue);
            }
        });
    }

    private void setupGenerateButton() {
        if (generateDescriptionButton != null) {
            generateDescriptionButton.setOnAction(event -> {
                if (!canGenerate) {
                    showAlert("Veuillez patienter", "Trop de requêtes envoyées. Veuillez attendre quelques instants avant de réessayer.", Alert.AlertType.INFORMATION);
                    return;
                }

                String titre = titreField.getText();
                if (titre == null || titre.trim().isEmpty()) {
                    showAlert("Erreur", "Veuillez d'abord saisir un titre pour le produit.", Alert.AlertType.ERROR);
                    return;
                }

                canGenerate = false;
                generateDescriptionButton.setDisable(true);
                
                scheduler.schedule(() -> {
                    canGenerate = true;
                    generateDescriptionButton.setDisable(false);
                }, 30, TimeUnit.SECONDS);

                try {
                    String description = produitService.generateDescription(titre);
                    descriptionField.setText(description);
                } catch (Exception e) {
                    showAlert("Erreur", "Une erreur est survenue lors de la génération de la description. Veuillez réessayer plus tard.", Alert.AlertType.ERROR);
                }
            });
        }
    }

    @FXML
    private void handleGenerateDescription(ActionEvent event) {
        if (!canGenerate) {
            showAlert("Veuillez patienter", "Trop de requêtes envoyées. Veuillez attendre quelques instants avant de réessayer.", Alert.AlertType.INFORMATION);
            return;
        }

        String titre = titreField.getText();
        if (titre == null || titre.trim().isEmpty()) {
            showAlert("Erreur", "Veuillez d'abord saisir un titre pour le produit.", Alert.AlertType.ERROR);
            return;
        }

        canGenerate = false;
        generateDescriptionButton.setDisable(true);
        
        scheduler.schedule(() -> {
            canGenerate = true;
            generateDescriptionButton.setDisable(false);
        }, 30, TimeUnit.SECONDS);

        try {
            String description = produitService.generateDescription(titre);
            descriptionField.setText(description);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la génération de la description. Veuillez réessayer plus tard.", Alert.AlertType.ERROR);
        }
    }

    public void setProduit(Produit produit) {
        this.selectedProduit = produit;
        if (produit != null) {
            titreField.setText(produit.getTitre());
            descriptionField.setText(produit.getDescription());
            artisteIdField.setText(String.valueOf(produit.getArtisteId()));
            prixField.setText(String.valueOf(produit.getPrix()));
            statutField.setText(produit.getStatut());
            dateCreationField.setValue(produit.getDateDeCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            categorieComboBox.setValue(produit.getCategorie());
            imageField.setText(produit.getImage());
        }
    }

    @FXML
    private void handleSaveProduit(ActionEvent event) {
        try {
            if (!validateFields()) return;

            if (selectedProduit == null) {
                selectedProduit = new Produit();
            }

            selectedProduit.setTitre(titreField.getText().trim());
            selectedProduit.setDescription(descriptionField.getText().trim());
            selectedProduit.setArtisteId(Integer.parseInt(artisteIdField.getText()));
            selectedProduit.setPrix(Float.parseFloat(prixField.getText()));
            selectedProduit.setStatut(statutField.getText().trim());
            selectedProduit.setDateDeCreation(Date.from(dateCreationField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            selectedProduit.setCategorie(categorieComboBox.getValue());
            selectedProduit.setImage(imageField.getText().trim());

            produitService.save(selectedProduit);
            showAlert("Succès", "Produit enregistré avec succès", Alert.AlertType.INFORMATION);
            closeWindow(event);

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la sauvegarde", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        StringBuilder errors = new StringBuilder();

        if (titreField.getText().trim().isEmpty()) {
            errors.append("- Le titre est obligatoire\n");
        } else if (titreField.getText().trim().length() < 3) {
            errors.append("- Le titre doit contenir au moins 3 caractères\n");
        }

        if (descriptionField.getText().trim().isEmpty()) {
            errors.append("- La description est obligatoire\n");
        }

        if (artisteIdField.getText().trim().isEmpty()) {
            errors.append("- L'ID de l'artiste est obligatoire\n");
        }

        if (prixField.getText().trim().isEmpty()) {
            errors.append("- Le prix est obligatoire\n");
        } else {
            try {
                float prix = Float.parseFloat(prixField.getText());
                if (prix <= 0) {
                    errors.append("- Le prix doit être supérieur à 0\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Le prix doit être un nombre valide\n");
            }
        }

        if (statutField.getText().trim().isEmpty()) {
            errors.append("- Le statut est obligatoire\n");
        }

        if (dateCreationField.getValue() == null) {
            errors.append("- La date de création est obligatoire\n");
        }

        if (categorieComboBox.getValue() == null) {
            errors.append("- La catégorie est obligatoire\n");
        }

        if (errors.length() > 0) {
            showAlert("Erreur de validation", errors.toString(), Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    @FXML
    private void handleBackToListProduit(ActionEvent event) {
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