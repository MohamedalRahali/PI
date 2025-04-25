package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Panier;
import models.Produit;
import Services.PaiementService;
import Services.PanierService;
import Services.PaymentService;

import java.io.IOException;

public class PanierController {
    @FXML private TableView<Produit> panierTable;
    @FXML private TableColumn<Produit, String> titreColumn;
    @FXML private TableColumn<Produit, Double> prixColumn;
    @FXML private TableColumn<Produit, Void> actionsColumn;
    @FXML private Label totalLabel;
    @FXML private Label nombreProduitsLabel;
    @FXML private Label reductionLabel;
    @FXML private TextField numeroCarteField;
    @FXML private TextField dateExpirationField;
    @FXML private TextField codeSecuriteField;

    private final PanierService panierService = new PanierService();
    private final PaymentService paymentService = new PaymentService();
    private final PaiementService paiementService = new PaiementService();

    @FXML
    public void initialize() {
        setupTableColumns();
        refreshPanier();
        setupValidators();
    }

    private void setupTableColumns() {
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        
        actionsColumn.setCellFactory(param -> new TableCell<Produit, Void>() {
            private final Button removeButton = new Button("Supprimer");
            {
                removeButton.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());
                    panierService.retirerProduit(produit);
                    refreshPanier();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
    }

    private void refreshPanier() {
        panierTable.setItems(panierService.getProduits());
        double total = panierService.calculerTotal();
        totalLabel.setText(String.format("Total : %.2f€", total));
        
        if (panierService.appliquerReduction()) {
            totalLabel.setText(totalLabel.getText() + " (Réduction de 30% appliquée)");
        }
        nombreProduitsLabel.setText(String.valueOf(panierService.getNombreProduits()));
        reductionLabel.setText(String.format("%.2f €", panierService.getReduction()));
    }

    private void setupValidators() {
        // Validateur pour le numéro de carte (16 chiffres)
        numeroCarteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeroCarteField.setText(oldValue);
            }
            if (newValue.length() > 16) {
                numeroCarteField.setText(oldValue);
            }
        });

        // Validateur pour la date d'expiration (MM/YY)
        dateExpirationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*/?\\d*")) {
                dateExpirationField.setText(oldValue);
            }
            if (newValue.length() > 5) {
                dateExpirationField.setText(oldValue);
            }
        });

        // Validateur pour le code de sécurité (3 ou 4 chiffres)
        codeSecuriteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                codeSecuriteField.setText(oldValue);
            }
            if (newValue.length() > 4) {
                codeSecuriteField.setText(oldValue);
            }
        });
    }

    @FXML
    private void handlePayer() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Paiement");
        dialog.setHeaderText("Entrez votre email pour recevoir la confirmation de paiement");
        dialog.setContentText("Email:");

        dialog.showAndWait().ifPresent(email -> {
            double montant = panierService.calculerTotal();
            paymentService.processPayment(email, montant);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement réussi");
            alert.setHeaderText(null);
            alert.setContentText("Un email de confirmation a été envoyé à " + email);
            alert.showAndWait();
            
            panierService.viderPanier();
            refreshPanier();
        });
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 