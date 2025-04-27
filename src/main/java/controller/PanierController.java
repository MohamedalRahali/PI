package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Produit;
import Services.ProduitService;

import java.io.IOException;
import java.util.Map;

public class PanierController {
    @FXML private TableView<Produit> panierTable;
    @FXML private TableColumn<Produit, String> titreColumn;
    @FXML private TableColumn<Produit, Double> prixColumn;
    @FXML private TableColumn<Produit, Integer> quantiteColumn;
    @FXML private Label totalLabel;
    @FXML private Label nombreProduitsLabel;
    @FXML private Label reductionLabel;

    private final ProduitService produitService = new ProduitService();

    @FXML
    public void initialize() {
        setupTableColumns();
        refreshPanier();
    }

    private void setupTableColumns() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
    }

    private void refreshPanier() {
        Map<Produit, Integer> panier = ManageProduitController.getPanier();
        ObservableList<Produit> produits = FXCollections.observableArrayList();
        
        double total = 0;
        int nombreProduits = 0;
        
        for (Map.Entry<Produit, Integer> entry : panier.entrySet()) {
            Produit produit = entry.getKey();
            int quantite = entry.getValue();
            produit.setQuantite(quantite);
            produits.add(produit);
            
            total += produit.getPrix() * quantite;
            nombreProduits += quantite;
        }
        
        panierTable.setItems(produits);
        totalLabel.setText(String.format("Total : %.2f€", total));
        nombreProduitsLabel.setText(String.valueOf(nombreProduits));
        
        // Calcul de la réduction (exemple : 10% pour plus de 5 articles)
        double reduction = nombreProduits > 5 ? total * 0.1 : 0;
        reductionLabel.setText(String.format("%.2f€", reduction));
    }

    @FXML
    private void handlePayer(ActionEvent event) {
        // TODO: Implémenter la logique de paiement
        showAlert("Paiement", "Fonctionnalité de paiement à implémenter", Alert.AlertType.INFORMATION);
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

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 